package com.scaledcode.searchapi.searchrequest.fieldmapping;

import com.scaledcode.searchapi.values.SearchType;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FieldMapper {
    private static final Map<FieldMapKey, String> fieldMap = Map.ofEntries(
            Map.entry(new FieldMapKey("title", SearchType.TEXT), "title"),
            Map.entry(new FieldMapKey("title", SearchType.KEYWORD), "title.raw"),
            Map.entry(new FieldMapKey("author", SearchType.TEXT), "author"),
            Map.entry(new FieldMapKey("author", SearchType.KEYWORD), "author.raw"),
            Map.entry(new FieldMapKey("isbn", SearchType.KEYWORD), "isbn"),
            Map.entry(new FieldMapKey("publication_date", SearchType.DATE), "publication_date"),
            Map.entry(new FieldMapKey("keyword", SearchType.TEXT), "keyword"),
            Map.entry(new FieldMapKey("keyword", SearchType.KEYWORD), "keyword.raw")
    );

    public static Optional<String> getFieldName(String fieldName, SearchType searchType) {
        return Optional.ofNullable(fieldMap.get(new FieldMapKey(fieldName, searchType)));
    }
}
