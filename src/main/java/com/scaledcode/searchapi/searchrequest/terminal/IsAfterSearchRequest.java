package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.scaledcode.searchapi.values.SearchType;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class IsAfterSearchRequest extends TerminalSearchRequest{
    private final boolean includeLowerBound;

    public IsAfterSearchRequest(String field, String value, boolean includeLowerBound) {
        super(field, value);
        this.includeLowerBound = includeLowerBound;
    }

    @Override
    public Query getSearch() {
        var builder = QueryBuilders.range()
                                   .field(getField(SearchType.DATE));

        if (includeLowerBound) {
            builder.gte(JsonData.of(getValue()));
        } else {
            builder.gt(JsonData.of(getValue()));
        }

        return new Query(builder.build());
    }

    @Override
    protected String getQueryTypeName() {
        return "Is after";
    }
}
