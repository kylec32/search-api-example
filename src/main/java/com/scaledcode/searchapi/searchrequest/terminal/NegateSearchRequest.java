package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;

//todo doesn't spit out message
public class NegateSearchRequest extends AbstractSearchRequest{
    private final AbstractSearchRequest searchRequest;

    public NegateSearchRequest(AbstractSearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    @Override
    public Query getSearch() {
        return new Query(QueryBuilders.bool().mustNot(searchRequest.getSearch()).build());
    }
}
