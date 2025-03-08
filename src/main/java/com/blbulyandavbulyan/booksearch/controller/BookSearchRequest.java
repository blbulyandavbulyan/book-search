package com.blbulyandavbulyan.booksearch.controller;

import com.blbulyandavbulyan.booksearch.service.search.BookSearchQuery;

public record BookSearchRequest(BookResponse.BookFilterField filterField,
                                String filterValue,
                                BookFacetField facetField,
                                String query) {
    public BookSearchQuery toBookSearchResource() {
        return BookSearchQuery.builder()
                .filterField(BookSearchQuery.BookFilterField.valueOf(filterField.name()))
                .filterValue(filterValue)
                .facetField(com.blbulyandavbulyan.booksearch.service.search.BookFacetField.valueOf(facetField.name()))
                .query(query)
                .build();
    }

}
