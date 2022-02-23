package com.scaledcode.searchapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Book {
    private String title;
    private String author;
    private String isbn;
    @JsonProperty("publication_date")
    private LocalDate publishDate;
    @JsonProperty("keyword")
    private List<String> categories;
}
