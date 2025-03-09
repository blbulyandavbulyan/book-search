package com.blbulyandavbulyan.booksearch.controller;

import com.blbulyandavbulyan.booksearch.service.search.BookFacetField;
import lombok.Builder;

@Builder
public record FacetResponse(String value, BookFacetField field, long valueCount) {
}
