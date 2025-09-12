package com.sagarvarule.stepdefinitions;

import com.sagarvarule.support.TestInfra;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class DestinationStepDefinitions {
    private final TestInfra testInfra;

    @When("user execute destination endpoint")
    public void userExecuteDestinationEndpoint() {
        testInfra.scenarioContext().setResponseCode(testInfra.endpointDestinations().getResponse());

    }

    @Then("response code is {int}")
    public void responseCodeIs(int code) {
        assertTrue(testInfra.scenarioContext().getResponseCode().equals(Integer.toString(code)));
    }
}
