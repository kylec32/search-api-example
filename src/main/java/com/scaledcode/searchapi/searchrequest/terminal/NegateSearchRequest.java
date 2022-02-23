package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;
import com.scaledcode.searchapi.searchrequest.composite.CompositeSearchRequest;

import java.util.List;

public class NegateSearchRequest extends CompositeSearchRequest {
    public NegateSearchRequest(List<AbstractSearchRequest> requests) {
        super(requests);
    }

    @Override
    public Query getSearch() {
        return new Query(QueryBuilders.bool().mustNot(getRequests().stream()
                                                                   .map(AbstractSearchRequest::getSearch)
                                                                   .toList()).build());
    }
}
