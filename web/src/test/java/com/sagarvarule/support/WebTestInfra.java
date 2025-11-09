package com.sagarvarule.support;

import com.sagarvarule.webdriver.WebDriverManager;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class WebTestInfra {
    private final WebDriverManager driverManager;
    private final WebScenarioContext context;
    private final WebTestRunProperties props;

    public WebTestInfra(WebDriverManager driverManager,
                        WebScenarioContext context,
                        WebTestRunProperties props) {
        this.driverManager = driverManager;
        this.context = context;
        this.props = props;
    }

    public WebDriverManager driverManager() {
        return driverManager;
    }

    public WebScenarioContext context() {
        return context;
    }

    public WebTestRunProperties props() {
        return props;
    }
}
