package com.example.demo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class DemoApplicationTests {

	@Test
	// @Test
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

	@Test
	void getPersonRequestByIdReturnsId() throws IOException, InterruptedException {
		// Arrange
		String url = "http://localhost:8080/api/v1/person";
		var payload = new HashMap<String, String>() {
			{
				put("name", "John DoeById");
			}
		};

		// Act
		postRequest(url, payload);
		HttpResponse<String> response2 = getRequest(url);
		String body = response2.body();
		String id = body.split("\"")[3];
		HttpResponse<String> response3 = getRequest(url + "/" + id);

		// Assert
		assert ((response3).body().contains("John DoeById"));
	}

	@Test
	void deletePersonRequestDeletesPerson() throws IOException, InterruptedException {
		// Arrange
		String url = "http://localhost:8080/api/v1/person";
		var payload = new HashMap<String, String>() {
			{
				put("name", "John DoeToDelete");
			}
		};

		// Act
		postRequest(url, payload);
		HttpResponse<String> response2 = getRequest(url);
		String body = response2.body();
		String id = body.split("\"")[3];
		HttpResponse<String> response3 = deleteRequest(url + "/" + id);

		// Assert
		assertEquals(200, response3.statusCode());
	}

	@Test
	void putPersonRequestUpdatesPerson() throws IOException, InterruptedException {
		// Arrange
		String url = "http://localhost:8080/api/v1/person";
		var payload1 = new HashMap<String, String>() {
			{
				put("name", "John DoeToUpdate");
			}
		};

		var payload2 = new HashMap<String, String>() {
			{
				put("name", "John DoeUpdated");
			}
		};

		// Act
		postRequest(url, payload1);
		HttpResponse<String> response2 = getRequest(url);
		String body = response2.body();
		String id = body.split("\"")[3];
		HttpResponse<String> response3 = putRequest(url + "/" + id, payload2);

		// Assert
		assertEquals(200, response3.statusCode());
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

		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper
				.writeValueAsString(payload);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.headers("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());

		System.out.println(response.body());
		return response;
	}

	HttpResponse<String> putRequest(String url, HashMap<String, String> payload)
			throws IOException, InterruptedException {

		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper
				.writeValueAsString(payload);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.headers("Content-Type", "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());

		System.out.println(response.body());
		return response;
	}

	HttpResponse<String> deleteRequest(String url)
			throws IOException, InterruptedException {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.headers("Content-Type", "application/json")
				.DELETE()
				.build();

		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());

		System.out.println(response.body());
		return response;
	}

}
