package com.blbulyandavbulyan.booksearch.service.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import com.blbulyandavbulyan.booksearch.model.BookDocument;
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
    public BookDocument parse(final Path bookPath) throws BookParseException {
        try (final var fileInputStream = Files.newInputStream(bookPath)) {
            final var epubBook = epubReader.readEpub(fileInputStream);

            return BookDocument.builder()
                    .id(UUID.randomUUID().toString())
                    .title(epubBook.getTitle())
                    .authors(getAuthorNamesFromEpubBook(epubBook))
                    .language(epubBook.getMetadata().getLanguage())
                    .content(getBookContentAsString(epubBook))
                    .fileName(bookPath.toString())
                    .build();
        }
        catch (IOException e){
            throw new BookParseException(e);
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
