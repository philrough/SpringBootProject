package com.example.demo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoApplicationStepDefinitions {

    private String url;
    private String actualAnswer;

    @Given("the rest url {string}")
    public void the_base_url(String restUrl) {
        url = "http://localhost:8080" + restUrl;
    }

    @When("i send a GET request to the server")
    public void i_send_a_get_request() throws IOException, InterruptedException {
        HttpResponse<String> getResponse = getRequest(url);
        actualAnswer = getResponse.body();
        System.out.println(actualAnswer);
    }

    @Then("the response should be {string}")
    public void the_response_should_be(String expectedAnswer) {
        assertEquals(expectedAnswer, actualAnswer);
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
}