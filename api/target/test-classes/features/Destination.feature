Feature: Destinations endpoint loads all the available destination cities
  Scenario: Valid destinations cities are returned
    When user execute destination endpoint
    Then response code is 200