package com.sagarvarule.utils;

import com.sagarvarule.appium.AppiumDriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenShotUtil {

    public static String getScreenShot(AppiumDriverManager driverManager) {
        try {
            AppiumDriver driver = driverManager.getDriver();
            
            if (driver == null) {
                throw new RuntimeException("AppiumDriver is not initialized. Cannot capture screenshot.");
            }
            
            // Capture screenshot as base64 string
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            
            return base64Screenshot;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to capture screenshot: " + e.getMessage(), e);
        }
    }
}
