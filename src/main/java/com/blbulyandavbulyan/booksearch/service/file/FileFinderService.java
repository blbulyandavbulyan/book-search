package com.blbulyandavbulyan.booksearch.service.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileFinderService {
    public Collection<Path> findAllFilesInDirectoryByExtension(Path directory, String extension) throws FileFinderException {
        try (Stream<Path> pathStream = Files.walk(directory)) {
            return pathStream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(extension))
                    .toList();
        } catch (Exception e) {
            throw new FileFinderException(e);
        }
    }
}
