package com.sagarvarule.stepdefinitions;

import com.sagarvarule.models.DestinationResponse;
import com.sagarvarule.services.GETDestinations;
import com.sagarvarule.support.TestInfra;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class DestinationStepDefinitions {
    private final TestInfra testInfra;
    private final GETDestinations endpointDestinations;

    @Given("the destination API endpoint is available")
    public void theDestinationApiEndpointIsAvailable() {
        // This step serves as documentation and setup verification
        // The actual endpoint availability is tested when we call it
        assertNotNull(endpointDestinations, "Destination service should be initialized");
    }

    @When("user execute destination endpoint")
    public void userExecuteDestinationEndpoint() {
        DestinationResponse destinationResponse = endpointDestinations.execute();
        testInfra.scenarioContext().setResponse(destinationResponse.getRawResponse());
        testInfra.scenarioContext().put("destinations", destinationResponse.getDestinations());
    }

    @When("I call the destination endpoint")
    public void iCallTheDestinationEndpoint() {
        DestinationResponse destinationResponse = endpointDestinations.execute();
        testInfra.scenarioContext().setResponse(destinationResponse.getRawResponse());
        testInfra.scenarioContext().put("destinations", destinationResponse.getDestinations());
        testInfra.scenarioContext().put("destinationResponse", destinationResponse);
    }

    @Then("response code is {int}")
    public void responseCodeIs(int code) {
        Response response = testInfra.scenarioContext().getResponse();
        assertEquals(code, response.getStatusCode());

        @SuppressWarnings("unchecked")
        List<String> destinations = (List<String>) testInfra.scenarioContext().get("destinations");
        assertTrue(!destinations.isEmpty(), "Destinations list should not be empty");
    }

    @Then("I should receive a successful response with status code {int}")
    public void iShouldReceiveASuccessfulResponseWithStatusCode(int expectedStatusCode) {
        Response response = testInfra.scenarioContext().getResponse();
        assertNotNull(response, "Response should not be null");
        assertEquals(expectedStatusCode, response.getStatusCode(), 
            "Expected status code " + expectedStatusCode + " but got " + response.getStatusCode());
    }

    @Then("the response should contain a list of destinations in JSON format")
    public void theResponseShouldContainAListOfDestinationsInJsonFormat() {
        Response response = testInfra.scenarioContext().getResponse();
        assertNotNull(response, "Response should not be null");
        
        // Verify content type is JSON
        String contentType = response.getContentType();
        assertTrue(contentType.contains("application/json") || contentType.contains("text/json"), 
            "Response content type should be JSON, but was: " + contentType);
        
        @SuppressWarnings("unchecked")
        List<String> destinations = (List<String>) testInfra.scenarioContext().get("destinations");
        assertNotNull(destinations, "Destinations list should not be null");
        assertTrue(destinations instanceof List, "Response should be a list");
    }

    @Then("the destinations list should not be empty")
    public void theDestinationsListShouldNotBeEmpty() {
        @SuppressWarnings("unchecked")
        List<String> destinations = (List<String>) testInfra.scenarioContext().get("destinations");
        assertNotNull(destinations, "Destinations list should not be null");
        assertFalse(destinations.isEmpty(), "Destinations list should not be empty");
    }

    @Then("the response should contain the following predefined destinations:")
    public void theResponseShouldContainTheFollowingPredefinedDestinations(DataTable dataTable) {
        @SuppressWarnings("unchecked")
        List<String> actualDestinations = (List<String>) testInfra.scenarioContext().get("destinations");
        assertNotNull(actualDestinations, "Destinations list should not be null");
        
        List<String> expectedDestinations = dataTable.asList();
        
        for (String expectedDestination : expectedDestinations) {
            assertTrue(actualDestinations.contains(expectedDestination), 
                "Expected destination '" + expectedDestination + "' not found in response. " +
                "Actual destinations: " + actualDestinations);
        }
    }

    @Then("the endpoint should be publicly accessible")
    public void theEndpointShouldBePubliclyAccessible() {
        Response response = testInfra.scenarioContext().getResponse();
        assertNotNull(response, "Response should not be null");
        
        // If we got a response, the endpoint is accessible
        // Additional check: ensure it's not a 401/403 (authentication/authorization error)
        int statusCode = response.getStatusCode();
        assertNotEquals(401, statusCode, "Endpoint should not require authentication");
        assertNotEquals(403, statusCode, "Endpoint should not be forbidden");
    }

    @Then("the response content type should be JSON")
    public void theResponseContentTypeShouldBeJson() {
        Response response = testInfra.scenarioContext().getResponse();
        assertNotNull(response, "Response should not be null");
        
        String contentType = response.getContentType();
        assertNotNull(contentType, "Content type should not be null");
        assertTrue(contentType.contains("application/json") || contentType.contains("text/json"), 
            "Response content type should be JSON, but was: " + contentType);
    }

    @Then("the destinations list should contain at least {int} destinations")
    public void theDestinationsListShouldContainAtLeastDestinations(int minCount) {
        @SuppressWarnings("unchecked")
        List<String> destinations = (List<String>) testInfra.scenarioContext().get("destinations");
        assertNotNull(destinations, "Destinations list should not be null");
        assertTrue(destinations.size() >= minCount, 
            "Expected at least " + minCount + " destinations, but got " + destinations.size() + 
            ". Actual destinations: " + destinations);
    }

    @Then("each destination should be a valid string")
    public void eachDestinationShouldBeAValidString() {
        @SuppressWarnings("unchecked")
        List<String> destinations = (List<String>) testInfra.scenarioContext().get("destinations");
        assertNotNull(destinations, "Destinations list should not be null");
        
        for (String destination : destinations) {
            assertNotNull(destination, "Each destination should not be null");
            assertFalse(destination.trim().isEmpty(), 
                "Each destination should not be empty or whitespace only. Found: '" + destination + "'");
            assertTrue(destination.length() > 0, "Each destination should have content");
        }
    }
}
