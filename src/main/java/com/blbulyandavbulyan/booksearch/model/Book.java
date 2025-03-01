package com.blbulyandavbulyan.booksearch.model;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import org.apache.solr.client.api.util.CoreApiParameters;
import org.apache.solr.client.solrj.beans.Field;

@Builder

public record Book(
        @Field("id")
        UUID id,

        @Field("title")
        String title,

        @Field("authors")
        List<String> authors,

        @Field("content")
        String content,

        @Field("language")
        String language) {
}
