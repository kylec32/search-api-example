package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.scaledcode.searchapi.values.SearchType;

public class IsSearchRequest extends TerminalSearchRequest{
    public IsSearchRequest(String field, String value) {
        super(field, value);
    }

    @Override
    public Query getSearch() {
        return new Query(QueryBuilders.match()
                                      .field(getField(SearchType.TEXT))
                                      .query(FieldValue.of(getValue()))
                                      .build());
    }

    @Override
    protected String getQueryTypeName() {
        return "Is";
    }
}
