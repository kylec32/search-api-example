package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

public class EqualsSearchRequest extends TerminalSearchRequest{
    public EqualsSearchRequest(String field, String value) {
        super(field, value);
    }

    @Override
    public Query getSearch() {
        return new Query(QueryBuilders.regexp()
                                      .field(getField() + ".raw")
                                      .value("\"" + getValue() + "\"")
                                      .build());
    }
}
