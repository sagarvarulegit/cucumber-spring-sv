package com.sagarvarule.stepdefinitions;

import com.sagarvarule.services.GETDestinations;
import com.sagarvarule.support.TestInfra;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;


import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class DestinationStepDefinitions {
    private final TestInfra testInfra;
    private final GETDestinations endpointDestinations;

    @When("user execute destination endpoint")
    public void userExecuteDestinationEndpoint() {
         Response response = endpointDestinations.getResponse();
         testInfra.scenarioContext().setResponse(response);

    }

    @Then("response code is {int}")
    public void responseCodeIs(int code) {
        Response response = testInfra.scenarioContext().getResponse();
        assertTrue(response.getStatusCode() == code);
    }
}
