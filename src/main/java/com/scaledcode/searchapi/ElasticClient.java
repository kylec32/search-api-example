package com.scaledcode.searchapi;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Getter;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ElasticClient {
    @Getter
    private ElasticsearchClient client;

    public ElasticClient(@Value("${test.elasticsearch.url}") String url,
                         @Value("${test.elasticsearch.username}") String username,
                         @Value("${test.elasticsearch.password}") String password) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        var host = url.split("//")[1].split(":")[0];
        var port = Integer.parseInt(url.split(":")[2]);

        RestClient restClient = RestClient.builder(new HttpHost(host, port))
                                          .setHttpClientConfigCallback(httpClientBuilder -> {
                                              httpClientBuilder.disableAuthCaching();
                                              return httpClientBuilder
                                                      .setDefaultCredentialsProvider(credentialsProvider);
                                          })
                                          .build();

        // Create the transport with a Jackson mapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper(mapper));

        // And create the API client
        client = new ElasticsearchClient(transport);
    }
}
