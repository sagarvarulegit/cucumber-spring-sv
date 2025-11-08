package com.sagarvarule.springconfig;

import io.cucumber.spring.CucumberContextConfiguration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(
        classes = com.sagarvarule.TestBootApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ComponentScan(basePackages = {"com.sagarvarule.stepdefinitions", "com.sagarvarule.support", "com.sagarvarule.hooks", "com.sagarvarule.webdriver", "com.sagarvarule.pages"})
@EnableConfigurationProperties(com.sagarvarule.support.WebTestRunProperties.class)
@TestPropertySource(locations = "classpath:properties/testrun.properties")
public class CucumberSpringConfig {
}
