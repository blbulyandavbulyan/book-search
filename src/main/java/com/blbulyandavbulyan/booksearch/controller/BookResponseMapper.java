package com.blbulyandavbulyan.booksearch.controller;

import com.blbulyandavbulyan.booksearch.model.Book;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchResource;
import com.blbulyandavbulyan.booksearch.service.search.FacetResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookResponseMapper {
    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.id())
                .title(book.title())
                .authors(book.authors())
                .content(book.content())
                .language(book.language())
                .build();
    }

    public BookSearchResponse toBookSearchResponse(BookSearchResource bookSearchResource) {
        List<Book> books = bookSearchResource.books();
        return BookSearchResponse.builder()
                .books(books.stream()
                        .map(this::toBookResponse)
                        .toList())
                .facets(bookSearchResource.facets()
                        .stream()
                        .map(this::toFacetResponse)
                        .toList())
                .numFound(books.size())
                .build();
    }

    private FacetResponse toFacetResponse(FacetResource facetResource) {
        return FacetResponse.builder()
                .value(facetResource.value())
                .field(BookFacetField.valueOf(facetResource.field().name()))
                .valueCount(facetResource.valueCount())
                .build();
    }
}
