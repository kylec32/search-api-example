package com.scaledcode.searchapi.controllers;

import com.scaledcode.searchapi.Client;
import com.scaledcode.searchapi.models.ExampleObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SearchController {
    @GetMapping("search")
    public String search() throws IOException {
        var client = Client.getClient();
        var results = client.search(s -> s
                        .index("test2")
                        .query(q -> q.matchAll(v -> v))
//                        .query(q -> q
//                                .term(t -> t
//                                        .field("name")
//                                        .value(v -> v.stringValue("bicycle"))
//                                ))
        , ExampleObject.class);

        System.out.println(results);
        return "hello";
    }
}
