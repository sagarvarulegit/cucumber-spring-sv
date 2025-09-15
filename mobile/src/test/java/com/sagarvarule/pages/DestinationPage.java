package com.sagarvarule.pages;

import com.sagarvarule.appium.AppiumDriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Scope("prototype") // important for parallel safety
public class DestinationPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"destination_selector\")")
    private WebElement whereToField;

    @AndroidFindBy(uiAutomator  = "new UiSelector().resourceId(\"date_range_selector\")")
    private WebElement whenField;

    public DestinationPage(AppiumDriverManager driverManager) {
        this.driver = (AndroidDriver) driverManager.getDriver();
        
        if (this.driver != null) {
            PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(5)), this);
            this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        } else {
            System.out.println("Warning: Driver is null, page elements will not be initialized");
            this.wait = null;
        }
    }

    public String getPageName() {
        return "Destination Page";
    }

    public boolean isDisplayed() {
        if (driver == null || wait == null) {
            System.out.println("Driver is null, returning false for isDisplayed()");
            return false;
        }
        return isWhereToFieldVisible() && isWhenFieldVisible();
    }

    public boolean isWhereToFieldVisible() {
        if (driver == null || wait == null) {
            System.out.println("Driver is null, returning false for isWhereToFieldVisible()");
            return false;
        }
        try {
            wait.until(ExpectedConditions.visibilityOf(whereToField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWhenFieldVisible() {
        if (driver == null || wait == null) {
            System.out.println("Driver is null, returning false for isWhenFieldVisible()");
            return false;
        }
        try {
            wait.until(ExpectedConditions.visibilityOf(whenField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DestinationPage clickWhereToField() {
        if (driver == null || wait == null) {
            System.out.println("Driver is null, cannot click WhereToField");
            return this;
        }
        wait.until(ExpectedConditions.elementToBeClickable(whereToField)).click();
        return this; // or return a new page, if navigation occurs
    }

    public DestinationPage clickWhenField() {
        if (driver == null || wait == null) {
            System.out.println("Driver is null, cannot click WhenField");
            return this;
        }
        wait.until(ExpectedConditions.elementToBeClickable(whenField)).click();
        return this;
    }
}
