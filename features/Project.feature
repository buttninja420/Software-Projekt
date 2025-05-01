Feature: Project things

Scenario: Create a new project
    Given an app that exist
    When a project with name "name1" is created
    Then the project with name "name1" exists

Scenario: Register a new activity
    Given a Project with name "Maintenance"
    When an employee who is not the project leader adds new activity with name "Cleaning"
    Then the Project has an activity named "Cleaning"

Scenario: Timeregistering
    Given a Project with name "Maintenance" and activity named "Cleaning"
    When an employee with UID "XX01" registers time for the activity "Cleaning" with 2 hours
    And activity "Cleaning" has 0 hours worked
    And activity "Cleaning" has 30 available hours
    Then activity "Cleaning" has 2 hours worked and 28 hours available

Scenario: Register a user as project leader
    Given a Project with name "Maintenance" and activity named "Cleaning" without a project leader
    When an employee with UID "ELLE" is registered to the project takes the role as project leader
    Then the Project with name "Maintenance" has a project leader with the UID "ELLE"

