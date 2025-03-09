package com.blbulyandavbulyan.booksearch.service.search;

import com.blbulyandavbulyan.booksearch.model.BookFields;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")//suppress warnings because this enum comes from request
public enum BookFacetField {
    LANGUAGE(BookFields.LANGUAGE),
    AUTHORS(BookFields.AUTHORS_KEYWORD);

    private final String fieldName;

    BookFacetField(String fieldName) {
        this.fieldName = fieldName;
    }
}
