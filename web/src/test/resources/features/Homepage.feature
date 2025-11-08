Feature: Homepage Feature

  Scenario: Verify homepage title
    Given user is on the homepage
    And user closes login or create account popup if displayed
    Then user dont see login or create account popup