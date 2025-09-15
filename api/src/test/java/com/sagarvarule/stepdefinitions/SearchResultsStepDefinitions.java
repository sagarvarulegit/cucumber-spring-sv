package com.sagarvarule.stepdefinitions;

import com.sagarvarule.models.Hotel;
import com.sagarvarule.models.SearchResultsResponse;
import com.sagarvarule.services.SearchResultsClient;
import com.sagarvarule.support.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class SearchResultsStepDefinitions {
    
    private final SearchResultsClient searchResultsClient;
    private final ScenarioContext scenarioContext;

    @When("I search for hotels in {string} from {string} to {string}")
    public void iSearchForHotelsInFromTo(String city, String fromDate, String toDate) {
        scenarioContext.setLastSearchCity(city);
        scenarioContext.setLastSearchFromDate(fromDate);
        scenarioContext.setLastSearchToDate(toDate);
        
        SearchResultsResponse response = searchResultsClient.searchHotels(city, fromDate, toDate);
        scenarioContext.setSearchResults(response);
        scenarioContext.setResponseCode(String.valueOf(response.getStatusCode()));
    }

    @Then("I should receive a successful response")
    public void iShouldReceiveASuccessfulResponse() {
        SearchResultsResponse results = scenarioContext.getSearchResults();
        assertNotNull(results, "Search results should not be null");
        assertEquals(200, results.getStatusCode(), "Response status should be 200");
    }

    @Then("the response should contain {int} hotels")
    public void theResponseShouldContainHotels(int expectedCount) {
        SearchResultsResponse results = scenarioContext.getSearchResults();
        assertNotNull(results, "Search results should not be null");
        assertEquals(expectedCount, results.getHotelCount(), 
            "Expected " + expectedCount + " hotels but got " + results.getHotelCount());
    }

    @Then("all hotels should be in {string}")
    public void allHotelsShouldBeIn(String expectedCity) {
        SearchResultsResponse results = scenarioContext.getSearchResults();
        assertNotNull(results, "Search results should not be null");
        assertTrue(results.hasHotels(), "Should have at least one hotel");
        
        for (Hotel hotel : results.getHotels()) {
            assertEquals(expectedCity, hotel.city(), 
                "Hotel " + hotel.name() + " should be in " + expectedCity);
        }
    }

    @Then("all hotels should be available from {string} to {string}")
    public void allHotelsShouldBeAvailableFromTo(String expectedFromDate, String expectedToDate) {
        SearchResultsResponse results = scenarioContext.getSearchResults();
        assertNotNull(results, "Search results should not be null");
        assertTrue(results.hasHotels(), "Should have at least one hotel");
        
        for (Hotel hotel : results.getHotels()) {
            assertEquals(expectedFromDate, hotel.availableFrom(), 
                "Hotel " + hotel.name() + " should be available from " + expectedFromDate);
            assertEquals(expectedToDate, hotel.availableTo(), 
                "Hotel " + hotel.name() + " should be available to " + expectedToDate);
        }
    }

    @Then("each hotel should have a name, city, availability dates, and image URL")
    public void eachHotelShouldHaveRequiredFields() {
        SearchResultsResponse results = scenarioContext.getSearchResults();
        assertNotNull(results, "Search results should not be null");
        assertTrue(results.hasHotels(), "Should have at least one hotel");
        
        for (Hotel hotel : results.getHotels()) {
            assertNotNull(hotel.name(), "Hotel name should not be null");
            assertFalse(hotel.name().trim().isEmpty(), "Hotel name should not be empty");
            
            assertNotNull(hotel.city(), "Hotel city should not be null");
            assertFalse(hotel.city().trim().isEmpty(), "Hotel city should not be empty");
            
            assertNotNull(hotel.availableFrom(), "Hotel availableFrom should not be null");
            assertFalse(hotel.availableFrom().trim().isEmpty(), "Hotel availableFrom should not be empty");
            
            assertNotNull(hotel.availableTo(), "Hotel availableTo should not be null");
            assertFalse(hotel.availableTo().trim().isEmpty(), "Hotel availableTo should not be empty");
            
            assertNotNull(hotel.imageUrl(), "Hotel imageUrl should not be null");
            assertFalse(hotel.imageUrl().trim().isEmpty(), "Hotel imageUrl should not be empty");
        }
    }

    @Then("the response should contain hotels with names:")
    public void theResponseShouldContainHotelsWithNames(io.cucumber.datatable.DataTable dataTable) {
        SearchResultsResponse results = scenarioContext.getSearchResults();
        assertNotNull(results, "Search results should not be null");
        
        java.util.List<String> expectedHotelNames = dataTable.asList();
        java.util.List<String> actualHotelNames = results.getHotels().stream()
            .map(Hotel::name)
            .toList();
        
        for (String expectedName : expectedHotelNames) {
            assertTrue(actualHotelNames.contains(expectedName), 
                "Expected hotel '" + expectedName + "' not found in results: " + actualHotelNames);
        }
    }
}
