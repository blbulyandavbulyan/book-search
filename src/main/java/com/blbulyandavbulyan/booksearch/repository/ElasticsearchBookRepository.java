package com.blbulyandavbulyan.booksearch.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.Suggestion;
import com.blbulyandavbulyan.booksearch.configuration.AliasesConfigurationProperties;
import com.blbulyandavbulyan.booksearch.model.BookFields;
import com.blbulyandavbulyan.booksearch.service.jackson.JacksonPropertyNamesResolver;
import com.blbulyandavbulyan.booksearch.service.search.BookResource;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchException;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchQuery;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchResource;
import com.blbulyandavbulyan.booksearch.service.search.FacetResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ElasticsearchBookRepository implements BookRepository {
    private final ElasticsearchClient elasticsearchClient;
    private final JacksonPropertyNamesResolver jacksonPropertyNamesResolver;
    private final AliasesConfigurationProperties aliasesConfigurationProperties;
    private static final String FACETS_AGGREGATION_NAME = "facets";

    @Override
    public Optional<BookResource> findById(String id) {
        try {
            GetResponse<BookResource> bookGetResponse = elasticsearchClient.get(gb -> gb
                    .source(b -> b.fields(jacksonPropertyNamesResolver.getPropertyNamesFor(BookResource.class)))
                    .index(aliasesConfigurationProperties.getBooksName())
                    .id(id), BookResource.class);
            return Optional.ofNullable(bookGetResponse.source());
        } catch (IOException e) {
            throw new BookSearchException("Failed to find book with id: %s".formatted(id), e);
        }
    }

    @Override
    public BookSearchResource searchBooks(BookSearchQuery bookSearchQuery) {
        try {
            SearchResponse<BookResource> search = elasticsearchClient.search(srb -> srb
                    .index(aliasesConfigurationProperties.getBooksName())
                    .source(b -> b.filter(sfb -> sfb.includes(jacksonPropertyNamesResolver.getPropertyNamesFor(BookResource.class))))
                    .query(QueryBuilders.bool(qb -> qb
                            .must(buildMainQuery(bookSearchQuery))
                            .filter(buildFilterQuery(bookSearchQuery))))
                    .aggregations(buildAggreagationsMap(bookSearchQuery)), BookResource.class);

            List<FacetResource> facetResources = search.aggregations()
                    .get(FACETS_AGGREGATION_NAME)
                    .sterms()
                    .buckets()
                    .array()
                    .stream()
                    .map(bucket -> new FacetResource(bucket.key()._toJsonString(), bookSearchQuery.facetField(), bucket.docCount()))
                    .toList();

            List<BookResource> books = search.hits()
                    .hits()
                    .stream()
                    .map(Hit::source)
                    .toList();
            return new BookSearchResource(books, facetResources);
        } catch (IOException e) {
            throw new BookSearchException("Failed to search books", e);
        }
    }

    @Override
    public List<BookResource> getSuggestedBooks(String query) {
        try {
            String suggestionKey = "autocomplete";
            return elasticsearchClient.search(b -> b.index(aliasesConfigurationProperties.getBooksName())
                    .source(sb -> sb.filter(sfb -> sfb.includes(jacksonPropertyNamesResolver.getPropertyNamesFor(BookResource.class))))
                    .suggest(sb -> sb
                            .text(query)
                            .suggesters(suggestionKey, fsb -> fsb
                                    .completion(cb -> cb
                                            .field(BookFields.SUGGEST)
                                            .size(3)
                                    )
                            )
                    ), BookResource.class)
                    .suggest()
                    .get(suggestionKey)
                    .stream()
                    .map(Suggestion::completion)
                    .flatMap(s -> s.options().stream())
                    .map(CompletionSuggestOption::source)
                    .toList();
        } catch (IOException e) {
            throw new BookSearchException("Failed to find suggestions for query: " + query, e);
        }
    }

    private static Map<String, Aggregation> buildAggreagationsMap(BookSearchQuery bookSearchQuery) {
        return Map.of(FACETS_AGGREGATION_NAME, Aggregation.of(ab -> ab.terms(ta -> ta.field(bookSearchQuery.facetField().getFieldName()))));
    }

    private static Query buildMainQuery(BookSearchQuery bookSearchQuery) {
        String query = bookSearchQuery.query();
        return bookSearchQuery.matchPhrase() ?
                QueryBuilders.matchPhrase(mpb -> mpb.field(BookFields.CONTENT).query(query)) :
                QueryBuilders.match(mqb -> mqb.field(BookFields.CONTENT).query(query));
    }

    private Query buildFilterQuery(BookSearchQuery bookSearchQuery) {
        final var filterField = bookSearchQuery.filterField();
        final var filterValue = bookSearchQuery.filterValue();
        final var filterFieldName = filterField.getFieldName();
        return switch (filterField) {// I know that switches is kind of not a good idea actually, but here because of new syntax,
            // I will get a compilation error, if I add another enum value
            case TITLE, AUTHORS -> QueryBuilders.match(qb -> qb.field(filterFieldName).query(filterValue));
            case LANGUAGE -> QueryBuilders.term(qb -> qb.field(filterFieldName).value(filterValue));
        };
    }
}
