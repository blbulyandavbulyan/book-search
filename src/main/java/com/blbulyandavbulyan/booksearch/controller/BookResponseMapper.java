package com.blbulyandavbulyan.booksearch.controller;

import com.blbulyandavbulyan.booksearch.service.search.BookResource;
import com.blbulyandavbulyan.booksearch.service.search.BookSearchResource;
import com.blbulyandavbulyan.booksearch.service.search.FacetResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookResponseMapper {
    public BookResponse toBookResponse(BookResource book) {
        return BookResponse.builder()
                .id(book.id())
                .title(book.title())
                .authors(book.authors())
                .fileName(book.fileName())
                .language(book.language())
                .build();
    }

    public BookSearchResponse toBookSearchResponse(BookSearchResource bookSearchResource) {
        List<BookResource> books = bookSearchResource.books();
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
                .field(facetResource.field())
                .valueCount(facetResource.valueCount())
                .build();
    }
}
