Feature: Destination Screen provides option to select Destination and DateRange

  Scenario: WhereTo and When fields are displayed
    Given user is at destination screen
    Then user should see whereTo and when fields

  @AI
  Scenario: AI Visual Test for screen "Find your Stay"
    Given user is at destination screen
    Then user should see following elements:
      | Status bar             |
      | Title "Find Your Stay" |
      | Theme toggle (moon)    |
      | Hero image card        |
      | Hero headline          |
      | Hero subtext           |
      | "Search Hotels" card   |
      | "Where to?" field      |
      | "When" field           |

  @AI
  Scenario: AI Visual Test for screen "Find your Stay" - Demo: Capture Issues
    Given user is at destination screen
    Then user should see following elements:
      | XYZ textbox |
