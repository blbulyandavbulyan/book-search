package com.blbulyandavbulyan.booksearch.service.parser;

import java.io.IOException;
import java.nio.file.Path;

import com.blbulyandavbulyan.booksearch.model.Book;

public interface BookParser {
    Book parse(Path bookPath) throws IOException;
}
