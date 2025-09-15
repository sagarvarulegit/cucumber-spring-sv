package com.sagarvarule.springconfig;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(
        classes = com.sagarvarule.TestMobileApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@EnableConfigurationProperties(com.sagarvarule.support.MobileTestRunProperties.class)
public class CucumberSpringConfig {
}
