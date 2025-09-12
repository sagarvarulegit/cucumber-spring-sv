package com.sagarvarule.http;

import org.springframework.stereotype.Component;

import com.sagarvarule.support.ApiTestRunProperties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Component
public class RestClient {
    private final ApiTestRunProperties props;

    public RestClient(ApiTestRunProperties props) {
        this.props = props;
    }

    private String buildUrl(String path) {
        return props.baseUrl() + path;
    }

    public Response get(String path) {
        String url = buildUrl(path);
        return RestAssured
            .given()
            .relaxedHTTPSValidation()
            .accept(ContentType.JSON)
            .when()
            .get(url)
            .then()
            .extract()
            .response();
    }
}
