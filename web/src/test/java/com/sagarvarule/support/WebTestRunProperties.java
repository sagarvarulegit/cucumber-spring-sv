package com.sagarvarule.support;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "webtestrun")
public record WebTestRunProperties (
    String env,
    String baseUrl
){}
