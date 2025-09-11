package com.sagarvarule.stepdefinitions;

import org.assertj.core.api.Assertions;
import org.springframework.util.Assert;

import com.sagarvarule.services.GETDestinations;
import com.sagarvarule.support.ScenarioContext;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DestinationStepDefinitions {
    private GETDestinations getDestinations;
    private ScenarioContext scenarioContext;

    public DestinationStepDefinitions(GETDestinations getDestinations, ScenarioContext scenarioContext){
        this.getDestinations = getDestinations;
        this.scenarioContext = scenarioContext;
    }

    @When("user execute destination endpoint")
    public void userExecuteDestinationEndpoint() {
       scenarioContext.setResponseCode(getDestinations.getResponse());

    }

    @Then("response code is {int}")
    public void responseCodeIs(int code) {
        assertTrue(scenarioContext.getResponseCode().equals(Integer.toString(code)));
    }
}
