package com.sagarvarule.utils;

import com.sagarvarule.appium.AppiumDriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScreenShotUtil {

    private static AppiumDriverManager driverManager;

    @Autowired
    public ScreenShotUtil(AppiumDriverManager driverManager) {
        ScreenShotUtil.driverManager = driverManager;
    }

    public static String getScreenShot() {
        try {
            if (driverManager == null) {
                System.out.println("DriverManager is null, cannot capture screenshot");
                return null;
            }
            
            AndroidDriver driver = driverManager.getDriver();
            if (driver == null) {
                System.out.println("Driver is null, cannot capture screenshot");
                return null;
            }
            String base64Screenshot = driver.getScreenshotAs(OutputType.BASE64);
            return base64Screenshot;
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}
