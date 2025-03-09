package com.blbulyandavbulyan.booksearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._helpers.bulk.BulkIngester;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.indices.*;
import com.blbulyandavbulyan.booksearch.configuration.ElasticSearchBulkConfiguration;
import com.blbulyandavbulyan.booksearch.model.Book;
import com.blbulyandavbulyan.booksearch.service.parser.BookParseException;
import com.blbulyandavbulyan.booksearch.service.parser.BookParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexingService {
    private final ElasticSearchBulkConfiguration bulkConfiguration;
    private final ElasticsearchClient elasticsearchClient;
    private final BookParser bookParser;
    private final FileFinderService fileFinderService;
    @Value("book-search.books-directory")
    private final Path booksDirectory;

    public void reindexBooks() {
        String booksIndex = "books-" + System.currentTimeMillis();
        try(BulkIngester<Object> bulkIngester = createBulkIngester()) {
            Collection<Path> epubBookPaths = fileFinderService.findAllFilesInDirectoryByExtension(booksDirectory, ".epub");
            for (Path epubBookPath : epubBookPaths) {
                try{
                    log.debug("Parsing book: {}", epubBookPath);
                    Book parsedBook = bookParser.parse(epubBookPath);
                    log.debug("Indexing book: {}", epubBookPath);
                    bulkIngester.add(BulkOperation.of(b -> b
                            .create(cb -> cb
                                    .index(booksIndex)
                                    .id(parsedBook.id())
                                    .document(parsedBook)
                            )));
                }
                catch (BookParseException e){
                    log.error("Failed to parse book: {}", epubBookPath, e);
                }
            }
            reassignOrCreateAliasWithoutDowntime("books", booksIndex);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BulkIngester<Object> createBulkIngester() {
        return BulkIngester.of(b -> b
                .client(elasticsearchClient)
                .maxOperations(bulkConfiguration.getMaxOperations())
                .maxSize(bulkConfiguration.getMaxSize())
                .flushInterval(bulkConfiguration.getFlushIntervalValue(), bulkConfiguration.getFlushIntervalTimeUnit()));
    }

    private void reassignOrCreateAliasWithoutDowntime(String aliasName, String newIndex) throws IOException {
        final var aliasUpdateBuilder = new UpdateAliasesRequest.Builder();
        if (aliasExists(aliasName)) {
            GetAliasResponse getAliasResponse = elasticsearchClient.indices().getAlias(b -> b.name(aliasName));
            // Add actions to remove the alias from its current indices
            getAliasResponse.result().forEach((oldIndex, aliasMetadata) ->
                    aliasUpdateBuilder.actions(action -> action.remove(r -> r.index(oldIndex).alias(aliasName)))
            );
        }

        // Add an action to assign the alias to the new index
        aliasUpdateBuilder.actions(action -> action.add(a -> a.index(newIndex).alias(aliasName)));

        // Execute the request atomically
        UpdateAliasesResponse updateAliasesResponse = elasticsearchClient.indices().updateAliases(aliasUpdateBuilder.build());

        if (updateAliasesResponse.acknowledged()) {
            log.info("Alias {} successfully reassigned to index {}", aliasName, newIndex);
        } else {
            throw new IOException("Failed to atomically update alias: " + aliasName + " to point to index: " + newIndex);
        }
    }

    /**
     * Checks if an alias exists in Elasticsearch.
     *
     * @param aliasName The alias name to check.
     * @return true if the alias exists; false otherwise.
     * @throws IOException if there is a problem communicating with Elasticsearch.
     */
    private boolean aliasExists(String aliasName) throws IOException {
        return elasticsearchClient.indices().existsAlias(e -> e.name(aliasName)).value();
    }

}
