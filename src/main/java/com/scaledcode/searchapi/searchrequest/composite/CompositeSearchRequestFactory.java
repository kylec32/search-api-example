package com.scaledcode.searchapi.searchrequest.composite;

import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;
import com.scaledcode.searchapi.searchrequest.terminal.NegateSearchRequest;
import com.scaledcode.searchapi.values.CompositeOperator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompositeSearchRequestFactory {
    public static CompositeSearchRequest getSearchRequest(List<AbstractSearchRequest> requests, CompositeOperator operator) {
        return switch (operator) {
            case AND -> new AndCompositeSearchRequest(requests);
            case OR -> new OrCompositeSearchRequest(requests);
            case NOT -> new NegateSearchRequest(requests);
        };
    }
}
