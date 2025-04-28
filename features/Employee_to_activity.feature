Feature: Activity Register #Jeppe

Scenario: Register to an activity as employee
  Given an activity with a project leader
  When an employee with UID "XX12" requests to join an activity
  Then the employee with UID "xx12" is added to the activity

Scenario: Register an employee for an activity as a project leader
  Given an activity with a project leader and free timeslots
  When the project leader with UID "XX20" registers an employee with UID "XX21" for the activity
  Then the employee with UID "XX21" is added to the activity