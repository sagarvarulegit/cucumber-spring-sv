package com.sagarvarule.stepdefinitions;

import com.sagarvarule.pages.DestinationPage;
import com.sagarvarule.pages.HomePage;
import com.sagarvarule.support.WebTestInfra;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import io.cucumber.java.en.Then;

@RequiredArgsConstructor
public class DestinationStepDefinitions {
    private final WebTestInfra infra;
    private final HomePage homePage;
     


    @Given("user is at destination screen on web")
    public void userIsAtDestinationScreenOnWeb() {
        homePage.isDisplayed();
    }

    @When("user selects a destination from the list")
    public void userSelectsADestinationFromTheList() {
        
    }

    @Then("selected destination should be displayed in the WhereTo field")
    public void selectedDestinationShouldBeDisplayedInTheWhereToField() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
