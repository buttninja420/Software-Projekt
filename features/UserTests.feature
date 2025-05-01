Feature: user methode tests

    Scenario: get availablity of a user
        Given An app with a user with UID: "PRIK" exists
        Then user with UID: "PRIK" is available
