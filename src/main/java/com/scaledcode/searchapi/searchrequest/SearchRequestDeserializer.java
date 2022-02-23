package com.scaledcode.searchapi.searchrequest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.scaledcode.searchapi.exceptions.OperatorParsingException;
import com.scaledcode.searchapi.exceptions.RequestParsingException;
import com.scaledcode.searchapi.searchrequest.composite.CompositeSearchRequestFactory;
import com.scaledcode.searchapi.searchrequest.terminal.TerminalSearchRequestFactory;
import com.scaledcode.searchapi.values.CompositeOperator;
import com.scaledcode.searchapi.values.SearchAction;

import java.io.IOException;
import java.util.List;

public class SearchRequestDeserializer extends StdDeserializer<AbstractSearchRequest> {
    private final ObjectMapper mapper = new ObjectMapper();
    private final CollectionType searchRequestCollection = mapper.getTypeFactory()
                                                                    .constructCollectionType(List.class, AbstractSearchRequest.class);

    public SearchRequestDeserializer() {
        this(null);
    }

    protected SearchRequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AbstractSearchRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        AbstractSearchRequest request;
        SearchAction operator;

        // Indicates that this is a terminal node
        if (node.has("field")) {
            operator = parseOperator(node);
            request = buildFieldRequest(node, operator);
        } else {
            List<AbstractSearchRequest> requests = parseRequestArray(node);

            request = CompositeSearchRequestFactory.getSearchRequest(requests, parseCompositeOperator(node));
        }
        return request;
    }

    private static CompositeOperator parseCompositeOperator(JsonNode node) {
        try {
            return CompositeOperator.valueOf(node.get("operator").asText());
        } catch (IllegalArgumentException e) {
            throw new OperatorParsingException("Failed to parse the boolean operator: " + node.get("operator").asText(), e);
        }
    }

    private static SearchAction parseOperator(JsonNode node) {
        try {
            return SearchAction.valueOf(node.get("operator").asText());
        } catch (IllegalArgumentException e) {
            throw new OperatorParsingException("Failed to parse the Operator or Operator not found: "
                                                       + node.get("operator").asText(), e);
        }
    }

    private List<AbstractSearchRequest> parseRequestArray(JsonNode node) {
        try {
            return mapper.convertValue(node.get("requests"), searchRequestCollection);
        } catch (RuntimeException e) {
            throw new RequestParsingException("Failed to parse the request", e);
        }
    }

    private static AbstractSearchRequest buildFieldRequest(JsonNode node, SearchAction operator) {
        String value = null;
        if (operator != SearchAction.IS_EMPTY && operator != SearchAction.IS_NOT_EMPTY) {
            value = node.get("value").asText();
        }

        return TerminalSearchRequestFactory.getSearchRequest(node.get("field").asText(), value, operator);
    }
}
