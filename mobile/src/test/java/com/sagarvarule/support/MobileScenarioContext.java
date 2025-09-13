package com.sagarvarule.support;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class MobileScenarioContext {
    private String currentScreen;
    private String lastAction;
    private boolean testPassed;
    private String errorMessage;
    private String elementText;

    public String getCurrentScreen() { 
        return currentScreen; 
    }
    
    public void setCurrentScreen(String currentScreen) { 
        this.currentScreen = currentScreen; 
    }
    
    public String getLastAction() { 
        return lastAction; 
    }
    
    public void setLastAction(String lastAction) { 
        this.lastAction = lastAction; 
    }
    
    public boolean isTestPassed() { 
        return testPassed; 
    }
    
    public void setTestPassed(boolean testPassed) { 
        this.testPassed = testPassed; 
    }
    
    public String getErrorMessage() { 
        return errorMessage; 
    }
    
    public void setErrorMessage(String errorMessage) { 
        this.errorMessage = errorMessage; 
    }

    public String getElementText() {
        return elementText;
    }

    public void setElementText(String elementText) {
        this.elementText = elementText;
    }
}
