Feature: Hotel Search Application - Destinations API
  As a user of the Hotel Search Application,
  I want to fetch a list of available destinations via an API endpoint,
  So that I can view and use these destinations while searching for hotels.

  Background:
    Given the destination API endpoint is available

  Scenario: Successfully retrieve list of destinations
    When I call the destination endpoint
    Then I should receive a successful response with status code 200
    And the response should contain a list of destinations in JSON format
    And the destinations list should not be empty

  Scenario: Verify predefined destinations are included
    When I call the destination endpoint
    Then I should receive a successful response with status code 200
    And the response should contain the following predefined destinations:
      | New York   |
      | London     |
      | Paris      |
      | Tokyo      |
      | Sydney     |
      | Dubai      |
      | Singapore  |
      | Rome       |
      | Barcelona  |
      | Istanbul   |

  Scenario: Validate destination endpoint accessibility
    When I call the destination endpoint
    Then the endpoint should be publicly accessible
    And I should receive a successful response with status code 200
    And the response content type should be JSON

  Scenario: Verify minimum required destinations count
    When I call the destination endpoint
    Then I should receive a successful response with status code 200
    And the destinations list should contain at least 10 destinations
    And each destination should be a valid string