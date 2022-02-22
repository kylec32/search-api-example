package com.scaledcode.searchapi.searchrequest;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = SearchRequestDeserializer.class)
public abstract class AbstractSearchRequest {
    @JsonIgnore
    public abstract Query getSearch();
}
