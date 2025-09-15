package com.sagarvarule.services;

import org.springframework.stereotype.Component;
import com.sagarvarule.http.RestClient;
import com.sagarvarule.models.SearchResultsResponse;
import io.restassured.response.Response;
import java.util.Map;
import java.util.HashMap;

@Component
public class SearchResultsClient extends BaseGetService<SearchResultsResponse> {
    private static final String PATH = "/api/searchResults";

    public SearchResultsClient(RestClient restClient) {
        super(restClient);
    }
    
    @Override
    public String getPath() {
        return PATH;
    }
    
    @Override
    protected SearchResultsResponse processResponse(Response response) {
        return new SearchResultsResponse(response);
    }
    
    public SearchResultsResponse searchHotels(String city, String fromDate, String toDate) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("city", city);
        queryParams.put("from_date", fromDate);
        queryParams.put("to_date", toDate);
        
        Response response = restClient.get(PATH, queryParams);
        return processResponse(response);
    }
}
