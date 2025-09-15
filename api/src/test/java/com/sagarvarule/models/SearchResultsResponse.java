package com.sagarvarule.models;

import io.restassured.response.Response;
import java.util.List;
import java.util.Arrays;

public class SearchResultsResponse {
    private final Response rawResponse;
    private final List<Hotel> hotels;

    public SearchResultsResponse(Response response) {
        this.rawResponse = response;
        this.hotels = extractHotels(response);
    }

    private List<Hotel> extractHotels(Response response) {
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            try {
                Hotel[] hotelArray = response.as(Hotel[].class);
                return Arrays.asList(hotelArray);
            } catch (Exception e) {
                return List.of();
            }
        }
        return List.of();
    }

    public int getStatusCode() {
        return rawResponse.statusCode();
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public String getRawBody() {
        return rawResponse.getBody().asString();
    }

    public int getHotelCount() {
        return hotels.size();
    }

    public boolean hasHotels() {
        return !hotels.isEmpty();
    }
}
