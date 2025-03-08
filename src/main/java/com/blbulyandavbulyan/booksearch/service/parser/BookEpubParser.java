package com.blbulyandavbulyan.booksearch.service.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import com.blbulyandavbulyan.booksearch.model.Book;
import lombok.RequiredArgsConstructor;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookEpubParser implements BookParser {
    private final EpubReader epubReader;

    @Override
    public Book parse(final Path bookPath) throws IOException {
        try (final var fileInputStream = Files.newInputStream(bookPath)) {
            final var epubBook = epubReader.readEpub(fileInputStream);

            return Book.builder()
                    .id(UUID.randomUUID().toString())
                    .title(epubBook.getTitle())
                    .authors(getAuthorNamesFromEpubBook(epubBook))
                    .language(epubBook.getMetadata().getLanguage())
                    .content(getBookContentAsString(epubBook))
                    .build();
        }
    }

    private static List<String> getAuthorNamesFromEpubBook(final nl.siegmann.epublib.domain.Book epubBook) {
        return epubBook.getMetadata()
                .getAuthors()
                .stream()
                .map(author -> author.getFirstname() + ' ' + author.getLastname())
                .toList();
    }

    private String getBookContentAsString(final nl.siegmann.epublib.domain.Book epubBook) throws IOException {

        //TODO: this might be a bit inefficient to do, it's better to index documents one by one
        // or maybe index some additional metadata related to the, like what page it is and so on

        final List<Resource> resources = epubBook.getResources()
                .getResourcesByMediaType(MediatypeService.XHTML);
        StringBuilder stringBuilder = new StringBuilder();

        for (final var resource : resources) {
            final Document parse = Jsoup.parse(new String(resource.getData(), resource.getInputEncoding()));
            stringBuilder.append(parse.text());
        }
        return stringBuilder.toString();
    }
}
