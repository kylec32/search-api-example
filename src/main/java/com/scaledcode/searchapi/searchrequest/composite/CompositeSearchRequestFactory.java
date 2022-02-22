package com.scaledcode.searchapi.searchrequest.composite;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;
import com.scaledcode.searchapi.values.BooleanOperator;

import java.util.List;

public abstract class CompositeSearchRequestFactory {
    public static CompositeSearchRequest getSearchRequest(List<AbstractSearchRequest> requests, BooleanOperator operator) {
        switch (operator) {
            case AND:
                return new AndCompositeSearchRequest(requests);
            case OR:
                return new OrCompositeSearchRequest(requests);
            default:
                throw new RuntimeException("Not implemented");
        }
    }
}
