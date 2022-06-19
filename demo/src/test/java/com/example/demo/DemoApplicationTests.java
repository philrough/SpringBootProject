package com.example.demo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void getPersonRequestReturnsNoPeopleOnInitialStartup() throws IOException, InterruptedException {
		// Arrange
		String url = "http://localhost:8080/api/v1/person";

		// Act
		HttpResponse<String> response = getRequest(url);

		// Assert
		assertEquals("[]",
				((HttpResponse<String>) response).body());
	}

	@Test
	void postPersonRequestAddsPerson() throws IOException, InterruptedException {
		// Arrange
		String url = "http://localhost:8080/api/v1/person";

		var payload = new HashMap<String, String>() {
			{
				put("name", "John Doe");
			}
		};

		// Act
		HttpResponse<String> response = postRequest(url, payload);

		// Assert
		assertEquals(200, response.statusCode());

	}

	@Test
	void getPersonRequestReturnsPerson() throws IOException, InterruptedException {
		// Arrange
		String url = "http://localhost:8080/api/v1/person";

		// Act
		HttpResponse<String> response = getRequest(url);

		// Assert
		assert ((response).body().contains("John Doe"));
	}

	// Helper methods
	HttpResponse<String> getRequest(String url) throws IOException, InterruptedException {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();

		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());

		System.out.println(response.body());

		return response;
	}

	HttpResponse<String> postRequest(String url, HashMap<String, String> payload)
			throws IOException, InterruptedException {

		var payload2 = new HashMap<String, String>() {
			{
				put("name", "John Doe");
			}
		};

		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper
				.writeValueAsString(payload2);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());

		System.out.println(response.body());
		return response;
	}

}
