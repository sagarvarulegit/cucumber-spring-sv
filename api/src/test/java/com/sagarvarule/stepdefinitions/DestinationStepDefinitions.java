package com.sagarvarule.stepdefinitions;

import com.sagarvarule.models.DestinationResponse;
import com.sagarvarule.services.GETDestinations;
import com.sagarvarule.support.TestInfra;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class DestinationStepDefinitions {
    private final TestInfra testInfra;
    private final GETDestinations endpointDestinations;

    @When("user execute destination endpoint")
    public void userExecuteDestinationEndpoint() {

        DestinationResponse destinationResponse = endpointDestinations.execute();

        testInfra.scenarioContext().setResponse(destinationResponse.getRawResponse());

        testInfra.scenarioContext().put("destinations", destinationResponse.getDestinations());
    }

    @Then("response code is {int}")
    public void responseCodeIs(int code) {
        Response response = testInfra.scenarioContext().getResponse();
        assertEquals(code, response.getStatusCode());

        @SuppressWarnings("unchecked")
        List<String> destinations = (List<String>) testInfra.scenarioContext().get("destinations");

        assertTrue(!destinations.isEmpty(), "Destinations list should not be empty");
    }
}
