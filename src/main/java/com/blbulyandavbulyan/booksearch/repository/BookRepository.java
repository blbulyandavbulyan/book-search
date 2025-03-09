package com.blbulyandavbulyan.booksearch.repository;

import com.blbulyandavbulyan.booksearch.service.search.BookResource;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchQuery;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchResource;

import java.util.Optional;

public interface BookRepository {
    Optional<BookResource> findById(String id);
    BookSearchResource searchBooks(BookSearchQuery bookSearchQuery);
}
