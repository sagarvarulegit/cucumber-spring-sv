package com.sagarvarule.hooks;

import com.sagarvarule.support.MobileTestInfra;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MobileHooks {
    private final MobileTestInfra infra;

    @Before
    public void beforeScenario() {
        // Driver will be created lazily when first accessed
        infra.context().setTestPassed(false);
    }

    @After
    public void afterScenario() {
        try {
            infra.driverManager().quitDriver();
        } catch (Exception e) {
            // Log error but don't fail the test
            System.err.println("Error closing driver: " + e.getMessage());
        }
    }
}
