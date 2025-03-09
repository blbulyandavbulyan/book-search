package com.blbulyandavbulyan.booksearch.service.parser;

import java.nio.file.Path;

import com.blbulyandavbulyan.booksearch.model.BookDocument;

/**
 * Main interface to parse book from file
 */
public interface BookParser {
    BookDocument parse(Path bookPath) throws BookParseException;
}