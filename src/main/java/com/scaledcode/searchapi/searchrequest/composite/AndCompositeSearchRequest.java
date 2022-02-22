package com.scaledcode.searchapi.searchrequest.composite;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;

import java.util.List;

public class AndCompositeSearchRequest extends CompositeSearchRequest {
    public AndCompositeSearchRequest(List<AbstractSearchRequest> requests) {
        super(requests);
    }

    @Override
    public Query getSearch() {
        var boolBuilder = new BoolQuery.Builder();

        boolBuilder.must(getRequests().stream().map(AbstractSearchRequest::getSearch).toList());

        return new Query(boolBuilder.build());
    }
}
