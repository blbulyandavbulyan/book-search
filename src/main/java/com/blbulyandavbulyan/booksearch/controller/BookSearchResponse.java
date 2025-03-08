package com.blbulyandavbulyan.booksearch.controller;

import lombok.Builder;

import java.util.List;

@Builder
public record BookSearchResponse(List<BookResponse> books,
                                 List<FacetResponse> facets,
                                 long numFound) {
}
