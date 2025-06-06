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
    Given a Project with name "Maintenance"
    When the project has 30 available hours and 0 hours worked
    And an employee with UID "XX01" registers time for project "Maintenance" with 2 hours
    Then project "Maintenance" has 2 hours worked and 28 hours available

Scenario: Register a user as project leader
    Given a Project with name "Maintenance" and activity named "Cleaning" without a project leader
    When an employee with UID "elle" is registered to the project takes the role as project leader
    Then the Project with name "Maintenance" has a project leader with the UID "elle"

Scenario: Check total assignmed time across all activities
    Given a Project with name "Maintenance" and activity with name "Conference room" with 2 hours worked
    And activity with name "Hallway" with 3 hours worked
    And activity with name "Bathroom" with 1 hour worked
    When the project leader checks the total assigned time across all activities
    Then the total assigned time is 6 hours

Scenario: A project leader wants to generate a report
    Given a Project with name "Maintenance" and activity with name "Conference room" with 2 hours worked with start Date 2023 5 5 and End date 2023 6 5
    And activity with name "Hallway" with 3 hours worked with start Date 2023 5 5 and End date 2023 6 5
    And activity with name "Bathroom" with 1 hour worked and no start date and end date
    When the project leader generates a report for the project
    Then the report contains the total assigned time of 6 hours and Start date 2023 5 5 and End date 2023 6 5

Scenario: Exceed registered hours
    Given a project with name "Testing" with 20 hours budgeted and 10 hours registered
    When an employee adds 15 hours to the project registered work time
    Then the project with name "Testing" does not add the registered time of 15 hours

Scenario: Project leader wants to check budgeted hours and check if registered hours exceeds
    Given a project with name "GetToWork" with 30 hours budgeted and 15 hours registered
    When the project leader checks the budgeted hours to be 30 hours
    And the project leader checks the registered hours to be 15 hours
    Then the project leader sees that the budgeted hours is 30 and that the registered hours is 15

Scenario: Edit previously registered work hours
    Given a project with name "Testing" with 20 hours budgeted and 10 hours registered
    When an employee adds 1 hour to the registered work time
    Then the project with name "Testing" now has 11 hours of registered time

Scenario: A project leader wants to edit budgeted time and change name of activity
    Given a project "park maintenance" and activity with name "Build bench" and a project leader with UID "XX20"
    When the project leader want to set budgeted time to 25 hours from 20 hours and change the name to "Demolish bench"
    Then the project "park maintenance" now has budgeted time 25 hours and activity with name "Demolish bench"

Scenario: A user wants to log negative hours
    Given a project with name "Weekend" and a project leader
    When the user tries to log -10 hours
    Then the user is not able to log negative hours

Scenario: A project leader tries to set end date before start date and start date in the past
    Given a project with name "Weekend" and a project leader
    When the project leader tries to set start date to 2023 10 1 and end date to 2023 9 1
    Then the project leader is not able to set start date to 2023 10 1 and end date to 2023 9 1

Scenario: A project leader wants to edit start and end date
    Given a project with name "Weekend" with start date 2023 10 1 and end date 2023 11 1 and a project leader
    When the project leader wants to edit start date to 2023 9 1 and end date to 2023 10 1
    Then the project with name "Weekend" now has start date to 2023 9 1 and end date to 2023 10 1

Scenario: A project leader tries to edit start date and end date incorrectly
    Given a project with name "Weekend" with start date 2023 10 1 and end date 2023 11 1 and a project leader
    When the project leader wants to edit start date to 2023 9 1 and end date to 2023 8 1
    Then the project with name "Weekend" does not change start date to 2023 9 1 and end date to 2023 8 1

Scenario: A project leader tries to generate a report without activities
    Given a project with name "Weekend" and a project leader
    When the project leader tries to generate a report without activities
    Then the project leader is not able to generate a report without activities

Scenario: A project leader tries to set start date incorrectly and adds 0 hours
    Given a project with name "Weekend" and start date 2023 8 1 and end date 2023 9 1 and a project leader
    When the project leader tries to set start date to 2023 10 1
    And the project leader tries to add 0 hours
    Then the project leader is not able to set start date to 2023 10 1 and add 0 hours