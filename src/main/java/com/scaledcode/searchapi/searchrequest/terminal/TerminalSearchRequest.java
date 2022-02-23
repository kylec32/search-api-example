package com.scaledcode.searchapi.searchrequest.terminal;

import com.scaledcode.searchapi.searchrequest.AbstractSearchRequest;
import com.scaledcode.searchapi.searchrequest.fieldmapping.FieldMapper;
import com.scaledcode.searchapi.values.SearchType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public abstract class TerminalSearchRequest extends AbstractSearchRequest {
    @Getter(value = AccessLevel.NONE)
    private String field;
    @Getter(value = AccessLevel.PROTECTED)
    private String value;

    protected String getField(SearchType searchType) {
        return FieldMapper.getFieldName(field, searchType)
                          .orElseThrow(() -> new IllegalArgumentException(getQueryTypeName() + " search on field " + field + " is not supported"));
    }

    protected abstract String getQueryTypeName();
}
