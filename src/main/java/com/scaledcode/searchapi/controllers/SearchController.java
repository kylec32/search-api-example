package com.scaledcode.searchapi.controllers;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.scaledcode.searchapi.ElasticClient;
import com.scaledcode.searchapi.models.Book;
import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class SearchController {
    private final ElasticClient elasticClient;

    private final String indexName;

    public SearchController(ElasticClient elasticClient,
                            @Value("${test.elasticsearch.index.name}") String indexName) {
        this.elasticClient = elasticClient;
        this.indexName = indexName;
    }

    @PostMapping("search")
    public List<Book> doSearch(@RequestBody AbstractSearchRequest request) throws IOException {
        return elasticClient.getClient()
                            .search(s -> s
                                        .index(indexName)
                                        .query(request.getSearch())
                                    , Book.class)
                            .hits()
                            .hits()
                            .stream()
                            .map(Hit::source)
                            .toList();
    }
}
