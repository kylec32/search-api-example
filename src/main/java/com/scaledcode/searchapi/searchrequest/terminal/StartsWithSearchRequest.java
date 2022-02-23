package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.scaledcode.searchapi.values.SearchType;

public class StartsWithSearchRequest extends TerminalSearchRequest{
    public StartsWithSearchRequest(String field, String value) {
        super(field, value);
    }

    @Override
    public Query getSearch() {
        return new Query(QueryBuilders.prefix().field(getField(SearchType.KEYWORD))
                .value(getValue())
                .build());
    }

    @Override
    protected String getQueryTypeName() {
        return "Starts with";
    }
}
