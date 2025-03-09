package com.blbulyandavbulyan.booksearch.service.search;

import com.blbulyandavbulyan.booksearch.model.BookFields;
import lombok.Getter;

@Getter
public enum BookFilterField {
    LANGUAGE(BookFields.LANGUAGE),
    AUTHORS(BookFields.AUTHORS),
    TITLE(BookFields.TITLE);

    private final String fieldName;

    BookFilterField(String fieldName) {
        this.fieldName = fieldName;
    }
}
