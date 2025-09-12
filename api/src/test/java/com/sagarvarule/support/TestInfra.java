package com.sagarvarule.support;

import org.springframework.stereotype.Component;

import com.sagarvarule.services.GETDestinations;

import io.cucumber.spring.ScenarioScope;

@Component
@ScenarioScope
public class TestInfra {
   
    private final ScenarioContext scenarioContext;
    private final ApiTestRunProperties apiTestRunProperties;

    public TestInfra(ScenarioContext scenarioContext, ApiTestRunProperties apiTestRunProperties){
        this.scenarioContext = scenarioContext;
        this.apiTestRunProperties = apiTestRunProperties;
    }

    public ScenarioContext scenarioContext() { return scenarioContext; }
    public ApiTestRunProperties apiTestRunProperties() { return apiTestRunProperties; }
    

}
