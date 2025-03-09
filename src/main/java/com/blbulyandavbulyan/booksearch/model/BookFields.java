package com.blbulyandavbulyan.booksearch.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookFields {
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String AUTHORS = "authors";
    public static final String CONTENT = "content";
    public static final String LANGUAGE = "language";
    public static final String FILE_NAME = "fileName";

    /**
     * This field should not be present in {@link BookDocument}, it is managed by elastic search itself
     */
    public static final String SUGGEST = "suggest";

    /**
     * Keyword field for authors
     */
    public static final String AUTHORS_KEYWORD = "authors.keyword";
}
