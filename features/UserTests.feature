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

Scenario: remove time registered of a user
    Given An app with a user with UID: "PRIK" exists
    And user with UID: "PRIK" has 5 hours worked
    When user registers -2 hours worked
    Then user with UID: "PRIK" has 3 hours worked today

Scenario: user tries to remove more time than is registered
    Given An app with a user with UID: "PRIK" exists
    And user with UID: "PRIK" has 5 hours worked
    When user registers -6 hours worked
    Then An error: "cant have less than 0 hours registered per day" is thrown 
    And user with UID: "PRIK" has 5 hours worked today 
 
Scenario: defines daily work time
    Given An app with a user with UID: "PRIK" exists
    When user defines daily work time to be 8 hours
    Then user with UID: "PRIK" has daily work time of 8 hours

Scenario: user checks for availability in time span
    Given An app with a user with UID: "PRIK" exists
    And user is registered to 20 activities in time span 2023 10 1 to 2023 11 1
    When user checks availability in time span 2023 10 1 to 2023 11 1
    Then user with UID: "PRIK" has reached max activities and is not able to register for activity in time span 2023 10 1 to 2023 11 1

Scenario: user tries to register for activity with no availability
    Given An app with a user with UID: "PRIK" exists
    And user is registered to 20 activities in time span 2023 10 1 to 2023 11 1
    When user tries to register for activity with start date 2023 10 1 and end date 2023 11 1
    Then user with UID: "PRIK" is not able to register for activity with start date 2023 10 1 and end date 2023 11 1

Scenario: user check work time today and other daily
    Given An app with a user with UID: "PRIK" exists
    And user has 0 hours worked today
    And user registers 5 hours today and 8 hours on date 2023 10 1
    When user checks work time today and on date 2023 10 1
    Then user with UID: "PRIK" has 5 hours worked today and 8 hours on date 2023 10 1

Scenario: user check availability for activity with overlapping time span
    Given An app with a user with UID: "PRIK" exists
    And user is registered to 2 activities in time span 2023 10 1 to 2023 11 1
    When user checks availability for activity with start date 2023 10 2 and end date 2023 11 3
    Then user with UID: "PRIK" is available for activity with start date 2023 10 2 and end date 2023 11 3

Scenario: user tries to remove more time than is registered
    Given An app with a user with UID: "PRIK" exists
    And user with UID: "PRIK" has 5 hours worked on date 2023 10 1
    When user registers -6 hours worked on date 2023 10 1
    Then An error: "cant have less than 0 hours registered per day" is thrown 
    And user with UID: "PRIK" has 5 hours worked on date 2023 10 1
        