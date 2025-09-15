package com.sagarvarule.http;

import org.springframework.stereotype.Component;

import com.sagarvarule.support.ApiTestRunProperties;
import com.sagarvarule.utils.CurlCommandGenerator;

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

    public Response get(String path, java.util.Map<String, Object> queryParams) {
        String url = buildUrl(path);
        Response response = RestAssured
            .given()
            .relaxedHTTPSValidation()
            .accept(ContentType.JSON)
            .queryParams(queryParams)
            .when()
            .get(url)
            .then()
            .extract()
            .response();
            
        // Store CURL command for debugging failed requests
        String curlCommand = CurlCommandGenerator.generateGetCurlCommand(url, queryParams);
        System.setProperty("last.curl.command", curlCommand);
        
        return response;
    }
}
