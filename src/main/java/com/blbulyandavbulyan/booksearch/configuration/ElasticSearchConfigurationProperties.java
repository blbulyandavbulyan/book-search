package com.blbulyandavbulyan.booksearch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@ConfigurationProperties(prefix = "elasticsearch")
@Getter
@Setter
public class ElasticSearchConfigurationProperties {
    private String url;
    private String apiKey;
    private String username;
    private String password;
    private Path caCrtPath;
}
