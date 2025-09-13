package com.sagarvarule.support;

import com.sagarvarule.appium.AppiumDriverManager;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class MobileTestInfra {
    private final AppiumDriverManager driverManager;
    private final MobileScenarioContext context;
    private final MobileTestRunProperties props;

    public MobileTestInfra(AppiumDriverManager driverManager, 
                          MobileScenarioContext context,
                          MobileTestRunProperties props) {
        this.driverManager = driverManager;
        this.context = context;
        this.props = props;
    }

    public AppiumDriverManager driverManager() { 
        return driverManager; 
    }
    
    public MobileScenarioContext context() { 
        return context; 
    }
    
    public MobileTestRunProperties props() { 
        return props; 
    }
}
