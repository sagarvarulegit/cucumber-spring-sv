package com.sagarvarule.stepdefinitions;

import com.sagarvarule.pages.DestinationPage;
import com.sagarvarule.support.MobileTestInfra;
import com.sagarvarule.utils.AIUtil;
import com.sagarvarule.utils.Prompts;
import com.sagarvarule.utils.ScreenShotUtil;

import io.cucumber.datatable.DataTable;
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


    @Then("user should see following elements:")
    public void user_should_see_following_elements(DataTable dt) {
        //Get Screenshot
        String elements = String.join(", ", dt.asList());
        String prompt = Prompts.VERIFY_SCREENSHOT_PROMPT.replace("{elements}", elements);
        String base64Screenshot = ScreenShotUtil.getScreenShot(infra.driverManager());
        
        // Get AI response with detailed failure information
        com.sagarvarule.utils.AIResponse aiResponse = AIUtil.getResponse(base64Screenshot, prompt);
        
        // Log the AI response for debugging
        System.out.println("AI Visual Test Response: " + aiResponse.getFullResponse());
        
        // Assert with detailed failure message including AI analysis
        assertTrue(aiResponse.isSuccess(), aiResponse.getFailureMessage(elements));
        
        infra.context().setTestPassed(true);
        infra.context().setLastAction("Verified elements are visible: " + elements);
    }
    
}
