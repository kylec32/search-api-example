package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.scaledcode.searchapi.values.SearchType;

public class IsBeforeSearchRequest extends TerminalSearchRequest{
    private final boolean includeUpperBound;

    public IsBeforeSearchRequest(String field, String value, boolean includeUpperBound) {
        super(field, value);
        this.includeUpperBound = includeUpperBound;
    }

    @Override
    public Query getSearch() {
        var builder = QueryBuilders.range()
                                                    .field(getField(SearchType.DATE));

        if (includeUpperBound) {
            builder.lte(JsonData.of(getValue()));
        } else {
            builder.lt(JsonData.of(getValue()));
        }

        return new Query(builder.build());
    }

    @Override
    protected String getQueryTypeName() {
        return "Is before";
    }
}
