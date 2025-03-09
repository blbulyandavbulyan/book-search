package com.blbulyandavbulyan.booksearch.service.search;

import lombok.Builder;

@Builder
public record BookSearchQuery(BookFilterField filterField,
                              String filterValue,
                              BookFacetField facetField,
                              String query,
                              boolean matchPhrase) {

}
