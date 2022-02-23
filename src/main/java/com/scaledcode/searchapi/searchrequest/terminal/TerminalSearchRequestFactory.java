package com.scaledcode.searchapi.searchrequest.terminal;

import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;
import com.scaledcode.searchapi.values.SearchAction;
import lombok.NoArgsConstructor;

import java.util.Collections;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TerminalSearchRequestFactory {
    public static AbstractSearchRequest getSearchRequest(String field, String value, SearchAction searchAction) {
        return switch (searchAction) {
            case IS -> new IsSearchRequest(field, value);
            case IS_AFTER -> new IsAfterSearchRequest(field, value, false);
            case IS_ON_OR_AFTER -> new IsAfterSearchRequest(field, value, true);
            case IS_BEFORE -> new IsBeforeSearchRequest(field, value, false);
            case IS_ON_OR_BEFORE -> new IsBeforeSearchRequest(field, value, true);
            case CONTAINS -> new ContainsSearchRequest(field, value);
            case DOES_NOT_CONTAIN -> new NegateSearchRequest(Collections.singletonList(new ContainsSearchRequest(field, value)));
            case ENDS_WITH -> new EndsWithSearchRequest(field, value);
            case TEXT_EQUALS -> new EqualsSearchRequest(field, value);
            case NOT_EQUALS -> new NegateSearchRequest(Collections.singletonList(new EqualsSearchRequest(field, value)));
            case IS_EMPTY -> new NegateSearchRequest(Collections.singletonList(new IsNotNullSearchRequest(field, value)));
            case IS_NOT_EMPTY -> new IsNotNullSearchRequest(field, value);
            case STARTS_WITH -> new StartsWithSearchRequest(field, value);
        };
    }
}
