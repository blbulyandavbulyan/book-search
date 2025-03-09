package com.blbulyandavbulyan.booksearch.service.search;

import lombok.Getter;

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
