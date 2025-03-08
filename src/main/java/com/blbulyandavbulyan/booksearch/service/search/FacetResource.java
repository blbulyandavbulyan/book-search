package com.blbulyandavbulyan.booksearch.service.search;

public record FacetResource(String value, BookFacetField field, long valueCount) {
}
