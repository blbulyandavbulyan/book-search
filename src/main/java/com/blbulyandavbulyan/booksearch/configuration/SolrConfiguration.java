package com.blbulyandavbulyan.booksearch.configuration;

import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfiguration {
    public Http2SolrClient http2SolrClient(SolrConfigurationProperties properties) {
        //TODO consider support for authorization in this configuration
        return new Http2SolrClient.Builder(properties.getUrl())
                .build();
    }
}
