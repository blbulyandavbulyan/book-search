package com.blbulyandavbulyan.booksearch.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class ElasticSearchConfiguration {
    @Bean
    public RestClient restClient(ElasticSearchConfigurationProperties properties) {
        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(properties.getSslFingerprint());

        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword()));
        return RestClient
                .builder(HttpHost.create(properties.getUrl()))
                .setHttpClientConfigCallback(hc -> hc
                        .setSSLContext(sslContext)
                        .setDefaultCredentialsProvider(credsProv)
                )
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
