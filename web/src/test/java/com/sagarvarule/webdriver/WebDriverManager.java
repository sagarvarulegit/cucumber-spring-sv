package com.sagarvarule.webdriver;

import com.sagarvarule.support.WebTestRunProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.stereotype.Component;

@Component
public class WebDriverManager {

    private final WebTestRunProperties props;
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public WebDriverManager(WebTestRunProperties props) {
        this.props = props;
    }

    public WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            driver = createDriver();
            driverThreadLocal.set(driver);
        }
        return driver;
    }

    private WebDriver createDriver() {
        return switch (props.browser().toLowerCase()) {
            case "firefox" -> {
                io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver();
            }
            case "safari" -> new SafariDriver();
            default -> {
                io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--remote-debugging-port=0");
                options.addArguments("--disable-extensions");
                if (Boolean.TRUE.equals(props.headless())) {
                    options.addArguments("--headless=new");
                    options.addArguments("--disable-gpu");
                }
                yield new ChromeDriver(options);
            }
        };
    }

    public void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}