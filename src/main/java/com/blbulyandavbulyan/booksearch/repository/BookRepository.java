package com.blbulyandavbulyan.booksearch.repository;

import java.util.List;

import com.blbulyandavbulyan.booksearch.model.Book;

public interface BookRepository {
    Book getBookById(String id);
    void save(Book book);
    List<Book> findBooks(String query);
    List<String> getQuerySuggestions(String query);
}
