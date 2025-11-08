package com.sagarvarule.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sagarvarule.webdriver.WebDriverManager;

@Component
@Scope("prototype")
public class HomePage extends BasePage {
    private WebDriver driver;

    @FindBy( css  = "[data-cy='close-Modal']")
    private WebElement closeButtonForPopup;

    @FindBy( css  = "[data-cy='LoginHeaderText']")
    private WebElement loginOrCreateAccountButton;

    public HomePage(WebDriverManager driverManager) {
        super(driverManager.getDriver());
        this.driver = driverManager.getDriver();
    }

    @Override
    public String getPageName() {
        return "Home Page";
    }

    public boolean isRegistrationPopupDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(closeButtonForPopup));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void closeRegistrationPopupIfDisplayed() {
        if (isRegistrationPopupDisplayed()) {
            wait.until(ExpectedConditions.elementToBeClickable(closeButtonForPopup)).click();
        }
    }

    @Override
    public boolean isDisplayed() {
        return loginOrCreateAccountButton.isDisplayed();
    }

}
