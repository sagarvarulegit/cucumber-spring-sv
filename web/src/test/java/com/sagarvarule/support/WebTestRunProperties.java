package com.sagarvarule.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for web test run
 */
@ConfigurationProperties(prefix = "webtestrun")
public record WebTestRunProperties (
    String env,
    String baseUrl,
    String browser,
    Boolean headless
){}
