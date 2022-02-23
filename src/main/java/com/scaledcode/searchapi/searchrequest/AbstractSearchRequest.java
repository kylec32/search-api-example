package com.scaledcode.searchapi.searchrequest;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = SearchRequestDeserializer.class)
public interface AbstractSearchRequest {
    @JsonIgnore
    Query getSearch();
}
