package com.sagarvarule.stepdefinitions;

import com.sagarvarule.pages.DestinationPage;
import com.sagarvarule.support.MobileTestInfra;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class DestinationStepDefinitions {
    private final MobileTestInfra infra;
    private final DestinationPage destinationPage;

    @Given("user is at destination screen")
    public void userIsAtDestinationScreen() {
        // Initialize driver and navigate to destination screen
        infra.driverManager().getDriver();
        infra.context().setLastAction("Navigated to destination screen");
        infra.context().setCurrentScreen("Destination Screen");
    }

    @Then("user should see whereTo and when fields")
    public void userShouldSeeWhereToAndWhenFields() {
        assertTrue(destinationPage.isWhereToFieldVisible(), "WhereTo field should be visible");
        assertTrue(destinationPage.isWhenFieldVisible(), "When field should be visible");
        infra.context().setTestPassed(true);
        infra.context().setLastAction("Verified whereTo and when fields are visible");
    }
}
