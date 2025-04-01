Feature: App capabilities

  Scenario: A new user is added to the program
    Given An app
    When A new user registers and enters a UID "ELLE"
    Then A user with the name "ELLE" exists
