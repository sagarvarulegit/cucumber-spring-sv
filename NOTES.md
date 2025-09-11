Project: Cucumber Spring Test Framework (cucumber-spring-sv)
Modules

api → test module for API scenarios

Java & Build

JDK: OpenJDK 21

Maven Compiler Plugin: 3.13.0

Surefire Plugin: 3.2.5 (JUnit 5 support)

Dependency Versions (pinned to work with Cucumber 7.18.0)

io.cucumber:cucumber-java:7.18.0 (test)

io.cucumber:cucumber-junit-platform-engine:7.18.0 (test)

io.cucumber:cucumber-spring:7.18.0 (test)

org.junit.jupiter:junit-jupiter:5.10.2 (test)

org.junit.platform:junit-platform-suite:1.10.2 (test)

org.springframework.boot:spring-boot-starter-test:3.5.5 (test, with exclusions for JUnit Jupiter/Platform to avoid conflicts)

📌 Decision: We use exclusions instead of BOM to pin JUnit versions because Cucumber 7.18.0 expects Platform 1.10.x.
📌 spring-boot-starter-test is test scope only (no Boot dependency in main).

Test Boot Application

To allow Spring DI in tests without requiring Boot in main:

src/test/java/com/sagarvarule/TestBootApplication.java

package com.sagarvarule;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sagarvarule")
public class TestBootApplication { }

Cucumber–Spring Bridge

src/test/java/com/sagarvarule/support/CucumberSpringConfig.java

package com.sagarvarule.support;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(
    classes = com.sagarvarule.TestBootApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class CucumberSpringConfig { }

JUnit 5 Runner

src/test/java/com/sagarvarule/api/runner/RunCucumberTest.java

package com.sagarvarule.api.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
    key = GLUE_PROPERTY_NAME,
    value = "com.sagarvarule.api.stepdefinitions,com.sagarvarule.support"
)
@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value = "pretty, summary, html:target/cucumber-report.html, json:target/cucumber-report.json"
)
public class RunCucumberTest { }

Example Feature

src/test/resources/features/destination.feature

Feature: Destination

  Scenario: Call destination API
    When user execute destination endpoint
    Then response code is 200

Example Bean (DI)

src/main/java/com/sagarvarule/api/clients/GETDestinations.java

package com.sagarvarule.api.clients;

import org.springframework.stereotype.Component;

@Component
public class GETDestinations {
    private final String path = "/api/destination";

    public String getPath() {
        return path;
    }

    public void getResponse() {
        System.out.println("Calling " + path);
        // TODO: Replace with RestAssured/WebClient call
    }
}

Example Step Definition

src/test/java/com/sagarvarule/api/stepdefinitions/DestinationStepDefinitions.java

package com.sagarvarule.api.stepdefinitions;

import com.sagarvarule.api.clients.GETDestinations;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DestinationStepDefinitions {

    private final GETDestinations getDestinations;

    // Constructor injection (recommended)
    public DestinationStepDefinitions(GETDestinations getDestinations) {
        this.getDestinations = getDestinations;
    }

    @When("user execute destination endpoint")
    public void userExecuteDestinationEndpoint() {
        System.out.println("Path: " + getDestinations.getPath());
        getDestinations.getResponse();
    }

    @Then("response code is {int}")
    public void responseCodeIs(int expected) {
        // TODO: Assert response code from ScenarioState
    }
}

Key Decisions So Far

✅ Using JUnit 5 + Cucumber runner with @Suite.

✅ Bootstrapped Spring context with TestBootApplication in test sources (not main).

✅ Enabled Spring DI inside step definitions (constructor injection).

✅ Feature files under src/test/resources/features.

✅ Version conflicts resolved by excluding Boot’s JUnit versions and pinning to Cucumber-compatible ones.

Next Possible Steps

Add a ScenarioState bean (@ScenarioScope) to share API responses between @When and @Then.

Replace System.out.println in GETDestinations#getResponse with a real HTTP call (RestAssured/WebClient).

Add profile-based configs (e.g., application-test.yml for base URLs).

Parallel execution with JUnit 5 (junit-platform.properties).