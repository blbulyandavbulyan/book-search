package com.blbulyandavbulyan.booksearch.controller;

import lombok.Builder;

import java.util.List;

@Builder
public record BookResponse(String id,
                           String title,
                           List<String> authors,
                           String fileName,
                           String language) {
    @SuppressWarnings("unused")// suppress warnings because these enums will come from the request
    public enum BookFilterField {
        LANGUAGE,
        AUTHORS,
        TITLE
    }
}
