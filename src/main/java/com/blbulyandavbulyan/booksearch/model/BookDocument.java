package com.blbulyandavbulyan.booksearch.model;

import java.util.List;

import lombok.Builder;

/**
 * This represents the document which will be indexed in elastic search
 * @param id id of the book
 * @param title title of the book
 * @param authors authors of the book
 * @param fileName full path in file system to the book
 * @param content content of the book
 * @param language language of the book
 */
@Builder
public record BookDocument(
        String id,
        String title,
        List<String> authors,
        String fileName,
        String content,
        String language) {
}
