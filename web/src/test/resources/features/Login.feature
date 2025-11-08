Feature: User Login

  Scenario: Successful login
    Given I am on the login page
    When I enter my credentials
    Then I should be logged in successfully
