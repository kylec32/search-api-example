package com.scaledcode.searchapi;

import com.scaledcode.searchapi.models.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@SpringBootApplication
public class SearchApiApplication implements CommandLineRunner {
	private final ElasticClient elasticClient;

	private final String indexName;

	private final String url;

	private final String username;

	private final String password;

	public SearchApiApplication(ElasticClient elasticClient,
								@Value("${test.elasticsearch.index.name}") String indexName,
								@Value("${test.elasticsearch.url}") String url,
								@Value("${test.elasticsearch.username}") String username,
								@Value("${test.elasticsearch.password}") String password) {
		this.elasticClient = elasticClient;
		this.indexName = indexName;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static void main(String[] args) {
		SpringApplication.run(SearchApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			elasticClient.getClient().indices().delete(d -> d.index("test2"));
		} catch (Exception e) {
			System.out.println("No index exists, delete not needed.");
		}

		createIndex(url, indexName, username, password);

		var book1 = Book.builder()
								.title("The Hobbit")
								.author("J.R.R. Tolkien")
								.isbn("0-395-19395-8")
								.publishDate(LocalDate.of(1937, 9,21))
								.categories(List.of("Fantasy", "Adventure"))
								.build();

		var book2 = Book.builder()
						.title("The Hunger Games")
						.author("Suzanne Collins")
						.isbn("978-0439023481")
						.publishDate(LocalDate.of(2008, 9,14))
						.categories(List.of("Drama", "Action"))
						.build();

		var book3 = Book.builder()
						.title("Harry Potter and the Philosopher's Stone")
						.author("J. K. Rowling")
						.isbn("978-0439708180")
						.publishDate(LocalDate.of(1997, 6,26))
						.categories(List.of("Fantasy"))
						.build();

		var book4 = Book.builder()
						.title("To Kill a Mockingbird")
						.author("Harper Lee")
						.isbn("0446310786")
						.publishDate(LocalDate.of(1960, 7,11))
						.categories(List.of("Historical Fiction"))
						.build();

		var book5 = Book.builder()
						.title("Pride and Prejudice")
						.author("Jane Austen")
						.isbn("978-0446135054")
						.publishDate(LocalDate.of(1813, 1,28))
						.categories(List.of("Classic Regency", "Romance"))
						.build();

		var books = List.of(book1, book2, book3, book4, book5);

		for (int i=0; i<books.size(); i++) {
			int index = i;
			elasticClient.getClient().index(indexer -> indexer.index(indexName).document(books.get(index)).id(Integer.toString(index)));
		}
	}

	private void createIndex(final String url,
							 final String indexName,
							 final String elasticUsername,
							 final String elasticPassword) throws IOException, InterruptedException {
		var mapping = "{\n"
				+ "\t\"mappings\": {\n"
				+ "\t\t\"properties\": {\n"
				+ "\t\t\t\"title\": {\n"
				+ "\t\t\t\t\"type\": \"text\",\n"
				+ "\t\t\t\t\"fields\": {\n"
				+ "\t\t\t\t\t\"raw\": {\n"
				+ "\t\t\t\t\t\t\"type\": \"keyword\",\n"
				+ "\t\t\t\t\t\t\"normalizer\": \"lowercase\"\n"
				+ "\t\t\t\t\t}\n"
				+ "\t\t\t\t}\n"
				+ "\t\t\t},\n"
				+ "\t\t\t\"author\": {\n"
				+ "\t\t\t\t\"type\": \"text\",\n"
				+ "\t\t\t\t\"fields\": {\n"
				+ "\t\t\t\t\t\"raw\": {\n"
				+ "\t\t\t\t\t\t\"type\": \"keyword\",\n"
				+ "\t\t\t\t\t\t\"normalizer\": \"lowercase\"\n"
				+ "\t\t\t\t\t}\n"
				+ "\t\t\t\t}\n"
				+ "\t\t\t},\n"
				+ "\t\t\t\"isbn\": {\n"
				+ "\t\t\t\t\"type\": \"keyword\"\n"
				+ "\t\t\t},\n"
				+ "\t\t\t\"publication_date\": {\n"
				+ "\t\t\t\t\"type\": \"date\"\n"
				+ "\t\t\t},\n"
				+ "\t\t\t\"keyword\": {\n"
				+ "\t\t\t\t\"type\": \"text\",\n"
				+ "\t\t\t\t\"fields\": {\n"
				+ "\t\t\t\t\t\"raw\": {\n"
				+ "\t\t\t\t\t\t\"type\": \"keyword\",\n"
				+ "\t\t\t\t\t\t\"normalizer\": \"lowercase\"\n"
				+ "\t\t\t\t\t}\n"
				+ "\t\t\t\t}\n"
				+ "\t\t\t}\n"
				+ "\t\t}\n"
				+ "\t}\n"
				+ "}";

		var authorizationHeader = Base64.getEncoder().encodeToString((elasticUsername + ":" + elasticPassword).getBytes(StandardCharsets.UTF_8));

		HttpClient client = HttpClient.newHttpClient();
		var createRequest = HttpRequest.newBuilder()
											   .PUT(HttpRequest.BodyPublishers.ofString(mapping))
												.uri(URI.create(url + "/" + indexName))
												.header("Content-Type", "application/json")
												.header("Authorization", "Basic " + authorizationHeader)
												.build();

		var response = client.send(createRequest, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() >= 400) {
			throw new RuntimeException("Issue creating index");
		}
	}
}
