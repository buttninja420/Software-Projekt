Feature: App capabilities

  Scenario: A new user is added to the program
    Given An app
    When A new user registers and enters a UID "ELLE"
    Then A user with the name "ELLE" exists


  Scenario: A new user tries to register with the name UID as an already registered user
  Given An app with a user with UID: "ELLE"
  When A new user registers and enters a UID "ELLE"
  Then An error: "User with UID: ELLE already exists!" is thrown
