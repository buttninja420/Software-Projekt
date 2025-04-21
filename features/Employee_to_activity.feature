Feature: Activity Register

Scenario: Register to an activity as employee
  Given an activity in a project
  When an employee with UID "XX12" requests to join an activity
  Then the employee with UID "xx12" is added to the activity
