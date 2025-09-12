package com.sagarvarule.stepdefinitions;

import org.assertj.core.api.Assertions;
import org.springframework.util.Assert;

import com.sagarvarule.services.GETDestinations;
import com.sagarvarule.support.ApiTestRunProperties;
import com.sagarvarule.support.ScenarioContext;
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
