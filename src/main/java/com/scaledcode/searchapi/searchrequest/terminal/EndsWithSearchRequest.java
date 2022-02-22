package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

public class EndsWithSearchRequest extends TerminalSearchRequest{
    public EndsWithSearchRequest(String field, String value) {
        super(field, value);
    }

    @Override
    public Query getSearch() {
        return new Query(QueryBuilders.wildcard()
                                      .field(getField() + ".raw")
                                      .value(getValue() + "*")
                                      .build());
    }
}
