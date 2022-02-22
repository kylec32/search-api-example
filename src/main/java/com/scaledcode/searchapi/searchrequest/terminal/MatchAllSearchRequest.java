package com.scaledcode.searchapi.searchrequest.terminal;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBase;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;

public class MatchAllSearchRequest extends AbstractSearchRequest {
    public Query getSearch() {
        //Query.Builder builder = new Query.Builder().matchAll(v -> v);
        QueryVariant blah = QueryBuilders.matchAll().build();
        return new Query(blah);
    }
}
