package com.sagarvarule.support;

import org.springframework.stereotype.Component;

import com.sagarvarule.services.GETDestinations;

import io.cucumber.spring.ScenarioScope;

@Component
@ScenarioScope
public class TestInfra {
    private final GETDestinations endpointDestinations;
    private final ScenarioContext scenarioContext;
    private final ApiTestRunProperties apiTestRunProperties;

    public TestInfra(GETDestinations endpointDestinations, ScenarioContext scenarioContext, ApiTestRunProperties apiTestRunProperties){
        this.endpointDestinations = endpointDestinations;
        this.scenarioContext = scenarioContext;
        this.apiTestRunProperties = apiTestRunProperties;
    }

    public ScenarioContext scenarioContext() { return scenarioContext; }
    public ApiTestRunProperties apiTestRunProperties() { return apiTestRunProperties; }
    public GETDestinations endpointDestinations() { return endpointDestinations; }

}
