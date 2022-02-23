package com.scaledcode.searchapi.searchrequest.fieldmapping;

import com.scaledcode.searchapi.values.SearchType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class FieldMapKey {
    private final String fieldName;
    private final SearchType type;
}
