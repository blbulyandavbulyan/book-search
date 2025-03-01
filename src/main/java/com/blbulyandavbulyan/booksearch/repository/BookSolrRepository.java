package com.blbulyandavbulyan.booksearch.repository;

import java.util.List;

import com.blbulyandavbulyan.booksearch.model.Book;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookSolrRepository implements BookRepository {
    private final Http2SolrClient http2SolrClient;

    @Override
    public Book getBookById(final String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(final Book book) {
        http2SolrClient.addBean("books", book);
        http2SolrClient.commit();
    }

    @Override
    public List<Book> findBooks(final String query) {
        new SolrQuery("")
        http2SolrClient.query("books", ).getBeans(Book.class)
        return List.of();
    }

    @Override
    public List<String> getQuerySuggestions(final String query) {
        return List.of();
    }
}
