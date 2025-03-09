package com.blbulyandavbulyan.booksearch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "elasticsearch.bulk")
public class ElasticSearchBulkConfiguration {
    /**
     * Max amount of operations, if exceeded the bulk request will be sent<br>
     * Set -1 to disable it
     */
    private int maxOperations = 100;

    /**
     * Max size in bytes, after which the request should be sent<br>
     * Set -1 to disable it
     */
    private long maxSize = 819200;


    /**
     * Time after which the request will be sent (if any other criteria didn't work) in units specified in
     * {@link ElasticSearchBulkConfiguration#flushIntervalTimeUnit}
     */
    private long flushIntervalValue = 1;

    /**
     * TimeUnit for {@link ElasticSearchBulkConfiguration#flushIntervalValue}
     */
    private TimeUnit flushIntervalTimeUnit = TimeUnit.SECONDS;
}
