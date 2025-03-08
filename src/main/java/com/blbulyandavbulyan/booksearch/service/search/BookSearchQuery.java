package com.blbulyandavbulyan.booksearch.service.search;

import lombok.Builder;
import lombok.Getter;

@Builder
public record BookSearchQuery(BookFilterField filterField,
                              String filterValue,
                              BookFacetField facetField,
                              String query) {
    @Getter
    public enum BookFilterField {
        LANGUAGE("language"),
        AUTHORS("authors"),
        TITLE("title");

        private final String fieldName;

        BookFilterField(String fieldName) {
            this.fieldName = fieldName;
        }
    }

}
