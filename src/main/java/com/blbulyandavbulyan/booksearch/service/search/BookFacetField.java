package com.blbulyandavbulyan.booksearch.service.search;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")//suppress warnings because this enum comes from request
public enum BookFacetField {
    LANGUAGE("language"),
    AUTHORS("authors.keyword");

    private final String fieldName;

    BookFacetField(String fieldName) {
        this.fieldName = fieldName;
    }
}
