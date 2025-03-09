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
    /**
     * URL to elastic search
     */
    private String url;

    /**
     * elastic search username
     */
    private String username;

    /**
     * elastic search password
     */
    private String password;

    /**
     * Path to elastic search CA certificate
     */
    private Path caCrtPath;
}
