package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.InlineScript;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.scaledcode.searchapi.values.SearchType;

public class IsNotNullSearchRequest extends TerminalSearchRequest{
    public IsNotNullSearchRequest(String field, String value) {
        super(field, value);
    }

    @Override
    public Query getSearch() {
        var fieldName = getField(SearchType.KEYWORD);
        var nullScript = new Script.Builder().inline(new InlineScript.Builder()
                                            .source("return (doc['" + fieldName + "'].size() != 0) && (doc['" + fieldName + "'].value.length() > 0)")
                                            .build()).build();
        return new Query(QueryBuilders.script().script(nullScript)
                                      .build());
    }

    @Override
    protected String getQueryTypeName() {
        return "Is not null";
    }
}
