package com.sagarvarule.services;

import org.springframework.stereotype.Component;

import com.sagarvarule.http.RestClient;

import io.restassured.response.Response;

@Component
public class GETDestinations {
    private static final String PATH = "/api/destination";
    private final RestClient rest;

    public GETDestinations(RestClient rest) {
        this.rest = rest;
    }

    public Response getResponse() {
        return rest.get(PATH);
    }
}
