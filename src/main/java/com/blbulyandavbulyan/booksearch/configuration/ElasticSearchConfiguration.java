package com.blbulyandavbulyan.booksearch.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {
    @Bean
    public RestClient restClient(ElasticSearchConfigurationProperties properties) {
        return RestClient.builder(HttpHost.create(properties.getUrl()))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + properties.getApiKey())
                })
                .build();
    }

    @Bean
    public ElasticsearchTransport elasticSearchTransport(RestClient restClient) {
        return new RestClientTransport(
                restClient, new JacksonJsonpMapper());
    }

    @Bean(destroyMethod = "close")
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport elasticsearchTransport) {
        return new ElasticsearchClient(elasticsearchTransport);
    }
}
