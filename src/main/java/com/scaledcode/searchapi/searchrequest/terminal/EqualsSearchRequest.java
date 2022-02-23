package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.scaledcode.searchapi.values.SearchType;

public class EqualsSearchRequest extends TerminalSearchRequest{
    public EqualsSearchRequest(String field, String value) {
        super(field, value);
    }

    @Override
    public Query getSearch() {
        return new Query(QueryBuilders.regexp()
                                      .field(getField(SearchType.KEYWORD) + ".raw")
                                      .value("\"" + getValue() + "\"")
                                      .build());
    }

    @Override
    protected String getQueryTypeName() {
        return "Equals";
    }
}
