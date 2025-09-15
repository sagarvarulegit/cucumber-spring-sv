package com.sagarvarule.appium;

import com.sagarvarule.support.MobileTestRunProperties;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

@Component
public class AppiumDriverManager {
    private final MobileTestRunProperties props;
    private AndroidDriver driver;

    public AppiumDriverManager(MobileTestRunProperties props) {
        this.props = props;
    }

    public AndroidDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    private AndroidDriver createDriver() {
        try {
            // Check if we have a valid APK file
            String apkPath = null;
            try {
                apkPath = findApkFile();
                // Validate APK file exists and is not our placeholder
                File apkFile = new File(apkPath);
                if (!apkFile.exists() || apkFile.length() < 1000) { // APK should be larger than 1KB
                    throw new RuntimeException("Invalid or placeholder APK file");
                }
            } catch (Exception e) {
                // If no valid APK found, create a mock driver for testing framework
                System.out.println("No valid APK found. Creating mock driver for framework testing.");
                return createMockDriver();
            }

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", props.platformName());
            capabilities.setCapability("deviceName", props.deviceName());
            capabilities.setCapability("platformVersion", props.platformVersion());
            capabilities.setCapability("app", apkPath);
            capabilities.setCapability("automationName", "UiAutomator2");
            capabilities.setCapability("autoGrantPermissions", props.autoGrantPermissions());
            
            // Session timeout configurations to prevent early termination
            capabilities.setCapability("newCommandTimeout", props.newCommandTimeout());
            capabilities.setCapability("sessionTimeout", props.sessionTimeout());
            capabilities.setCapability("noReset", true);
            capabilities.setCapability("fullReset", false);

            URL serverUrl = URI.create(props.appiumServerUrl()).toURL();
            AndroidDriver androidDriver = new AndroidDriver(serverUrl, capabilities);
            androidDriver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(props.implicitWaitSeconds())
            );
            return androidDriver;
        } catch (Exception e) {
            System.out.println("Failed to create real Appium driver: " + e.getMessage());
            System.out.println("Creating mock driver for framework testing.");
            return createMockDriver();
        }
    }

    private AndroidDriver createMockDriver() {
        // For framework testing without real device/APK
        throw new RuntimeException("Mock driver not implemented. Please provide a valid APK file and ensure Appium server is running on " + props.appiumServerUrl());
    }

    private String findApkFile() {
        // Try to find APK in test/resources/apk directory
        String apkPath = "src/test/resources/apk";
        Path apkDir = Paths.get(apkPath);
        File apkDirectory = apkDir.toFile();
        
        // If not found, try relative to current working directory
        if (!apkDirectory.exists()) {
            apkDir = Paths.get("mobile/src/test/resources/apk");
            apkDirectory = apkDir.toFile();
        }
        
        if (!apkDirectory.exists()) {
            throw new RuntimeException("APK directory not found. Tried: " + apkPath + " and mobile/src/test/resources/apk");
        }

        File[] apkFiles = apkDirectory.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".apk") && 
            (props.apkFileName() == null || props.apkFileName().isEmpty() || name.equals(props.apkFileName()))
        );

        if (apkFiles == null || apkFiles.length == 0) {
            throw new RuntimeException("No APK file found in " + apkDir);
        }

        if (apkFiles.length > 1 && (props.apkFileName() == null || props.apkFileName().isEmpty())) {
            throw new RuntimeException("Multiple APK files found. Specify apkFileName in properties");
        }

        return apkFiles[0].getAbsolutePath();
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
