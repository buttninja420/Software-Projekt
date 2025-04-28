Feature: App capabilities

  Scenario: A new user is added to the program
    Given An app
    When A new user registers and enters a UID "ELLE"
    Then A user with the name "ELLE" exists


  Scenario: A new user tries to register with the name UID as an already registered user
    Given An app with a user with UID: "ELLE"
    When A new user registers and enters an existing UID "ELLE"
    Then registration fails

