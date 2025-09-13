package com.sagarvarule.models;

import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;

public class DestinationResponse {
    private final Response rawResponse;
    private final List<String> destinations;

    public DestinationResponse(Response response) {
        this.rawResponse = response;
        this.destinations = parseDestinations(response);
    }

    private List<String> parseDestinations(Response response) {

        return Arrays.asList(response.as(String[].class));
    }

    public Response getRawResponse() {
        return rawResponse;
    }

    public int getStatusCode() {
        return rawResponse.getStatusCode();
    }

    public List<String> getDestinations() {
        return destinations;
    }
}
