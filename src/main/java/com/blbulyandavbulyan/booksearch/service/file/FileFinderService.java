package com.blbulyandavbulyan.booksearch.service.file;

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
    /**
     * @param directory to search files
     * @param extension which file should end with
     * @return all files in directory which end with extension
     * @throws FileFinderException if IOException occurred, it will be wrapped in this exception
     */
    public Collection<Path> findAllFilesInDirectoryByExtension(Path directory, String extension) throws FileFinderException {
        try (Stream<Path> pathStream = Files.walk(directory)) {
            return pathStream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(extension))
                    .toList();
        } catch (IOException e) {
            throw new FileFinderException(e);
        }
    }
}
