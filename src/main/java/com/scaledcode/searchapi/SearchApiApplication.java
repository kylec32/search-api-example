package com.scaledcode.searchapi;

import co.elastic.clients.elasticsearch.core.CreateRequest;
import com.scaledcode.searchapi.models.ExampleObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SearchApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var client = Client.getClient();

		client.indices().delete(d -> d.index("test2"));
		client.indices().create(c -> c.index("test2"));

		var item = new ExampleObject("thisIsItem1", "thisIsItem2", 4);

		client.index(i -> i.index("test2").document(item).id("1"));
	}
}
