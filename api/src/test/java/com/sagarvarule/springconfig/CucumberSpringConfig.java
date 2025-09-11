package com.sagarvarule.springconfig;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(
        classes = com.sagarvarule.TestBootApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class CucumberSpringConfig {
}
