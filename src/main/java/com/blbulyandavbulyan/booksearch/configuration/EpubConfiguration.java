package com.blbulyandavbulyan.booksearch.configuration;

import nl.siegmann.epublib.epub.EpubReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EpubConfiguration {
    @Bean
    public EpubReader epubReader() {
        return new EpubReader();
    }
}
