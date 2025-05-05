Feature: user methode tests

    Scenario: get availablity of a user
        Given An app with a user with UID: "PRIK" exists
        Then user with UID: "PRIK" is available

    Scenario: register time of a user
        Given An app with a user with UID: "PRIK"
        When user registers 5 hours worked
        Then user with UID: "PRIK" has 5 hours worked today
