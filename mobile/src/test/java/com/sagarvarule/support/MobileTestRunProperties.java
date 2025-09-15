package com.sagarvarule.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mobiletest")
public record MobileTestRunProperties(
    String platformName,
    String deviceName,
    String platformVersion,
    String appiumServerUrl,
    String apkFileName,
    int implicitWaitSeconds,
    boolean autoGrantPermissions,
    int newCommandTimeout,
    int sessionTimeout,
    String browserstackApp,
    String browserstackUsername,
    String browserstackAccessKey,
    boolean useBrowserstack
){}
