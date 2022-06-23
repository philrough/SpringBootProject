Feature: Rest API Integraion test for the Demo Application
  Tests to verify the GET, POST, PUT and Delete Rest methods

  Scenario: GET Person Request Returns No People On Initial Startup
    Given the rest url "/api/v1/person"
    When i send a GET request to the server
    Then the response should be "[]"
