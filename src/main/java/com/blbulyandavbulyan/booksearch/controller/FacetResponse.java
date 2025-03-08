package com.blbulyandavbulyan.booksearch.controller;

import lombok.Builder;

@Builder
public record FacetResponse(String value, BookFacetField field, long valueCount) {
}
