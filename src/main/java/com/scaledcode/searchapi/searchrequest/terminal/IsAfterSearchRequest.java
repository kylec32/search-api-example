package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;

public class IsAfterSearchRequest extends TerminalSearchRequest{
    private final boolean includeLowerBound;

    public IsAfterSearchRequest(String field, String value, boolean includeLowerBound) {
        super(field, value);
        this.includeLowerBound = includeLowerBound;
    }

    @Override
    public Query getSearch() {
        var builder = QueryBuilders.range()
                                   .field(getField());

        if (includeLowerBound) {
            builder.gte(JsonData.of(getValue()));
        } else {
            builder.gt(JsonData.of(getValue()));
        }

        return new Query(builder.build());
    }
}
