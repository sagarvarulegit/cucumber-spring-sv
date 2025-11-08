package com.sagarvarule.pages;

import com.sagarvarule.webdriver.WebDriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DestinationPage extends BasePage {

    @FindBy(id = "destination_selector")
    private WebElement whereToField;

    @FindBy(id = "date_range_selector")
    private WebElement whenField;

    public DestinationPage(WebDriverManager driverManager) {
        super(driverManager.getDriver());
    }

    public String getPageName() {
        return "Destination Page";
    }

    public boolean isDisplayed() {
        return isWhereToFieldVisible() && isWhenFieldVisible();
    }

    public boolean isWhereToFieldVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(whereToField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWhenFieldVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(whenField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DestinationPage clickWhereToField() {
        wait.until(ExpectedConditions.elementToBeClickable(whereToField)).click();
        return this;
    }

    public DestinationPage clickWhenField() {
        wait.until(ExpectedConditions.elementToBeClickable(whenField)).click();
        return this;
    }
}
