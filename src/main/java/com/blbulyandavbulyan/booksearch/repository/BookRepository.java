package com.blbulyandavbulyan.booksearch.repository;

import com.blbulyandavbulyan.booksearch.model.Book;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchQuery;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchResource;

import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(String id);
    BookSearchResource searchBooks(BookSearchQuery bookSearchQuery);
}
