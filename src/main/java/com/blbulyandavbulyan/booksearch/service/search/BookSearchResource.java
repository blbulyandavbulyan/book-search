package com.blbulyandavbulyan.booksearch.service.search;

import java.util.List;

public record BookSearchResource(List<BookResource> books, List<FacetResource> facets) {
}
