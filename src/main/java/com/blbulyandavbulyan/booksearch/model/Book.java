package com.blbulyandavbulyan.booksearch.model;

import java.util.List;

import lombok.Builder;

@Builder
public record Book(
        String id,
        String title,
        List<String> authors,
        String content,
        String language) {
}
