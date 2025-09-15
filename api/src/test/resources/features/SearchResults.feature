Feature: Hotel Search Results API
  As a user
  I want to search for hotels by city and date range
  So that I can find available accommodations

  @CRM-19
  Scenario: Search for hotels in New York
    When I search for hotels in "New York" from "2023-12-15" to "2023-12-20"
    Then I should receive a successful response
    And the response should contain 3 hotels
    And all hotels should be in "New York"
    And all hotels should be available from "2023-12-15" to "2023-12-20"
    And each hotel should have a name, city, availability dates, and image URL

  @CRM-20
  Scenario: Verify specific hotels in New York search results
    When I search for hotels in "New York" from "2023-12-15" to "2023-12-20"
    Then I should receive a successful response
    And the response should contain hotels with names:
      | Grand NYC Hotel  |
      | Central Park Inn |
      | Empire Suites    |

  @CRM-21
  Scenario: Search for hotels with different date ranges
    When I search for hotels in "New York" from "2023-11-01" to "2023-11-05"
    Then I should receive a successful response
    And all hotels should be in "New York"
    And each hotel should have a name, city, availability dates, and image URL

  @CRM-22
  Scenario: Search for hotels in different cities
    When I search for hotels in "Paris" from "2023-12-15" to "2023-12-20"
    Then I should receive a successful response
    And all hotels should be in "Paris"
    And each hotel should have a name, city, availability dates, and image URL
