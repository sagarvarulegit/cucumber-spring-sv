package com.sagarvarule.stepdefinitions;

import com.sagarvarule.services.GETDestinations;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class DestinationStepDefinitions {
    @Autowired
    GETDestinations getDestinations;
    @When("user execute destination endpoint")
    public void userExecuteDestinationEndpoint() {
        System.out.println(":::::::::::::::::::::::::::::::::---------------------------------------");
        System.out.println(getDestinations.getResponse());

    }

    @Then("response code is {int}")
    public void responseCodeIs(int arg0) {

    }
}
