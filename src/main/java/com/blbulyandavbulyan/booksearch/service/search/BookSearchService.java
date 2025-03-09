package com.blbulyandavbulyan.booksearch.service.search;

import com.blbulyandavbulyan.booksearch.model.Book;
import com.blbulyandavbulyan.booksearch.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookRepository bookRepository;

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public BookSearchResource searchBooks(BookSearchQuery bookSearchQuery) {
        return bookRepository.searchBooks(bookSearchQuery);
    }
}
