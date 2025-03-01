package com.blbulyandavbulyan.booksearch;

import java.io.IOException;
import java.nio.file.Path;

import com.blbulyandavbulyan.booksearch.model.Book;
import com.blbulyandavbulyan.booksearch.service.BookEpubParser;
import nl.siegmann.epublib.epub.EpubReader;

public class TestEpubService {
    //FIXME do not commit this
    public static void main(String[] args) throws IOException {
        final BookEpubParser bookEpubParser = new BookEpubParser(new EpubReader());
        final Book book = bookEpubParser.parse(Path.of("C:\\Users\\david_blbulyan\\Downloads\\chesterton-man-who-knew-too-much.epub"));
        System.out.println(book.title());
        System.out.println(book.authors());
    }
}
