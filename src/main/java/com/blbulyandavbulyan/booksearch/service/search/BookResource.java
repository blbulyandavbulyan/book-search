package com.blbulyandavbulyan.booksearch.service.search;

import java.util.List;

/**
 * This class represents book object, retrieved from ElasticSearch
 * @param id id of the book
 * @param title title of the book
 * @param authors authors of the book
 * @param fileName full path in file system to the book
 * @param language language of the book
 */
public record BookResource(String id,
                           String title,
                           String fileName,
                           List<String> authors,
                           String language) {
}
