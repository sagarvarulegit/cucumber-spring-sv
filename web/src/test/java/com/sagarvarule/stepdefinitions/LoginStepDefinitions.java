package com.sagarvarule.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class LoginStepDefinitions {

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Given");
    }

    @When("I enter my credentials")
    public void i_enter_my_credentials() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("When");
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Then");
    }
}
