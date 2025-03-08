package com.blbulyandavbulyan.booksearch.service.search;

import com.blbulyandavbulyan.booksearch.model.Book;

import java.util.List;

public record BookSearchResource(List<Book> books, List<FacetResource> facets) {
}
