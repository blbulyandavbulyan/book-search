package com.blbulyandavbulyan.booksearch.repository;

import com.blbulyandavbulyan.booksearch.service.search.BookResource;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchQuery;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchResource;

import java.util.List;
import java.util.Optional;

/**
 * Repository for books<br>
 * It doesn't include save operation, because saving is a bit complicated
 */
public interface BookRepository {
    Optional<BookResource> findById(String id);
    BookSearchResource searchBooks(BookSearchQuery bookSearchQuery);
    List<BookResource> getSuggestedBooks(String query);
}
