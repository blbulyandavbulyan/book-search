package com.blbulyandavbulyan.booksearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileFinderService {
    Collection<Path> findAllFilesInDirectoryByExtension(Path directory, String extension) throws IOException {
        try (Stream<Path> pathStream = Files.walk(directory)) {
            return pathStream.filter(Files::isRegularFile)
                    .filter(path -> path.endsWith(extension))
                    .toList();
        }
    }
}
