package com.blbulyandavbulyan.booksearch.controller;

import com.blbulyandavbulyan.booksearch.service.search.BookFacetField;
import com.blbulyandavbulyan.booksearch.service.search.BookFilterField;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchQuery;

public record BookSearchRequest(BookFilterField filterField,
                                String filterValue,
                                BookFacetField facetField,
                                String query,
                                boolean matchPhrase) {
    public BookSearchQuery toBookSearchResource() {
        return BookSearchQuery.builder()
                .filterField(filterField)
                .filterValue(filterValue)
                .facetField(facetField)
                .query(query)
                .matchPhrase(matchPhrase)
                .build();
    }

}
