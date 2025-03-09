package com.blbulyandavbulyan.booksearch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "elasticsearch.aliases")
@Getter
@Setter
public class AliasesConfigurationProperties {
    /**
     * Alias name for books index
     */
    private String booksName = "books";
}
