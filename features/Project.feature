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

Scenario: Check total assignmed time across all activities
    Given a Project with name "Maintenance" and activity with name "Conference room" with 2 hours worked
    And activity with name "Hallway" with 3 hours worked
    And activity with name "Bathroom" with 1 hour worked
    When the project leader checks the total assigned time across all activities
    Then the total assigned time is 6 hours

Scenario: A project leader wants to generate a report
    Given a Project with name "Maintenance" and activity with name "Conference room" with 2 hours worked with start Date 2023 5 5 and End date 2023 6 5
    And activity with name "Hallway" with 3 hours worked with start Date 2023 5 5 and End date 2023 6 5
    And activity with name "Bathroom" with 1 hour worked with start Date 2023 5 5 and End date 2023 6 5
    When the project leader generates a report for the project
    Then the report contains the total assigned time of 6 hours and Start date 2023 5 5 and End date 2023 6 5
