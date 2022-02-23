package com.scaledcode.searchapi.searchrequest.fieldmapping;

import com.scaledcode.searchapi.values.SearchType;

public record FieldMapKey(String fieldName, SearchType type) {
}
