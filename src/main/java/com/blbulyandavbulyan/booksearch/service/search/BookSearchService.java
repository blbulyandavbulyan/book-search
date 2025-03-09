package com.blbulyandavbulyan.booksearch.service.search;

import com.blbulyandavbulyan.booksearch.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookRepository bookRepository;

    public Optional<BookResource> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public BookSearchResource searchBooks(BookSearchQuery bookSearchQuery) {
        return bookRepository.searchBooks(bookSearchQuery);
    }

    public List<BookResource> getSuggestedBooks(String query) {
        return bookRepository.getSuggestedBooks(query);
    }
}
