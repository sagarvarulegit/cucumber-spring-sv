package com.sagarvarule.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.sagarvarule.pages.HomePage;
import com.sagarvarule.support.WebTestInfra;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HomepageStepDfinitions {
    private final WebTestInfra infra;
    private final HomePage homePage;

    @Given("user is on the homepage")
    public void userIsOnTheHomepage() {
        infra.driverManager().getDriver()
             .navigate()
             .to(infra.props().baseUrl());
        infra.context().setLastAction("Navigated to homepage");
        
        
    }

    @Given("user closes login or create account popup if displayed")
    public void user_closes_login_or_create_account_popup_if_displayed() {
        homePage.closeRegistrationPopupIfDisplayed();  
    }

    @Then("user dont see login or create account popup")
    public void user_dont_see_login_or_create_account_popup() {
        assertFalse(homePage.isRegistrationPopupDisplayed(), "Login or Create Account popup should not be displayed");
    }
}
