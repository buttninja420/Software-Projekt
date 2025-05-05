Feature: user methode tests

Scenario: get availablity of a user
    Given An app with a user with UID: "PRIK" exists
    And user is not registered to any activity
    When user checks availability in time span 2023 10 1 to 2023 11 1
    Then user with UID: "PRIK" is available in time span 2023 10 1 to 2023 11 1

Scenario: register time of a user
    Given An app with a user with UID: "PRIK" exists
    When user registers 5 hours worked
    Then user with UID: "PRIK" has 5 hours worked today


