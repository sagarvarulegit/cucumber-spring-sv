package com.sagarvarule.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import com.sagarvarule.support.ScenarioContext;

@RequiredArgsConstructor
public class ApiTestHooks {
    
    private final ScenarioContext scenarioContext;

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            // Log CURL command for failed scenarios
            String curlCommand = System.getProperty("last.curl.command");
            if (curlCommand != null && !curlCommand.trim().isEmpty()) {
                scenario.log("🔍 CURL Command for Failed Request:");
                scenario.log(curlCommand);
                scenario.log(""); // Empty line for readability
                
                // Also log response details if available
                if (scenarioContext.getSearchResults() != null) {
                    scenario.log("📋 Response Details:");
                    scenario.log("Status Code: " + scenarioContext.getSearchResults().getStatusCode());
                    scenario.log("Response Body: " + scenarioContext.getSearchResults().getRawBody());
                }
            }
        }
        
        // Clear the stored CURL command for next test
        System.clearProperty("last.curl.command");
    }
}
