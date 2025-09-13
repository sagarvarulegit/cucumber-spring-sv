package com.sagarvarule.services;

import org.springframework.stereotype.Component;

import com.sagarvarule.http.RestClient;
import com.sagarvarule.models.DestinationResponse;

import io.restassured.response.Response;

@Component
public class GETDestinations extends BaseGetService<DestinationResponse> {
    private static final String PATH = "/api/destination";

    public GETDestinations(RestClient restClient) {
        super(restClient);
    }
    
    @Override
    public String getPath() {
        return PATH;
    }
    
    @Override
    protected DestinationResponse processResponse(Response response) {
        return new DestinationResponse(response);
    }
    
}
