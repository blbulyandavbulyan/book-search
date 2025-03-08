package com.blbulyandavbulyan.booksearch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "elasticsearch")
@Getter
@Setter
public class ElasticSearchConfigurationProperties {
    private String url;
    private String apiKey;
}
