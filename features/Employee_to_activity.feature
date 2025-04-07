Feature: Activity Register

Scenario: Register to an activity as employee
  Given an activity with a project leader
  When an employee with UID "XX12" requests to join an activity
  Then the employee with UID "xx12" is added to the activity
