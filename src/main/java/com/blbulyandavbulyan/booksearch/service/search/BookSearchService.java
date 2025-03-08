package com.blbulyandavbulyan.booksearch.service.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.blbulyandavbulyan.booksearch.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final ElasticsearchClient elasticsearchClient;

    public Optional<Book> getBookById(String id) {
        try {
            GetResponse<Book> bookGetResponse = elasticsearchClient.get(gb -> gb.id(id), Book.class);
            return Optional.ofNullable(bookGetResponse.source());
        } catch (IOException e) {
            throw new BookSearchException("Failed to find book with id: %s".formatted(id), e);
        }
    }

    public BookSearchResource searchBooks(BookSearchQuery bookSearchQuery) {
        try {
            SearchResponse<Book> search = elasticsearchClient.search(srb -> srb
                    .query(QueryBuilders.bool(qb -> qb
                            .must(buildMainQuery(bookSearchQuery))
                            .filter(buildFilterQuery(bookSearchQuery))))
                    .aggregations(buildAggreagationsMap(bookSearchQuery)), Book.class);

            List<FacetResource> facetResources = search.aggregations()
                    .get("facets")
                    .sterms()
                    .buckets()
                    .array()
                    .stream()
                    .map(bucket -> new FacetResource(bucket.key()._toJsonString(), bookSearchQuery.facetField(), bucket.docCount()))
                    .toList();

            List<Book> books = search.hits()
                    .hits()
                    .stream()
                    .map(Hit::source)
                    .toList();
            return new BookSearchResource(books, facetResources);
        } catch (IOException e) {
            throw new BookSearchException("Failed to search books", e);
        }
    }

    private static Map<String, Aggregation> buildAggreagationsMap(BookSearchQuery bookSearchQuery) {
        return Map.of("facets", Aggregation.of(ab -> ab.terms(ta -> ta.field(bookSearchQuery.facetField().getFieldName()))));
    }

    private static Query buildMainQuery(BookSearchQuery bookSearchQuery) {
        return QueryBuilders.match(mqb -> mqb.field("content").query(bookSearchQuery.query()));
    }

    private Query buildFilterQuery(BookSearchQuery bookSearchQuery) {
        final var filterField = bookSearchQuery.filterField();
        final var filterValue = bookSearchQuery.filterValue();
        final var filterFieldName = filterField.getFieldName();
        return switch (filterField) {
            case TITLE, AUTHORS -> QueryBuilders.match(qb -> qb.field(filterFieldName).query(filterValue));
            case LANGUAGE -> QueryBuilders.term(qb -> qb.field(filterFieldName).value(filterValue));
        };
    }
}
