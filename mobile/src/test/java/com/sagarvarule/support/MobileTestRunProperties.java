package com.sagarvarule.support;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "apitestrun")
public record MobileTestRunProperties(
    String env,
    String baseUrl
){}
