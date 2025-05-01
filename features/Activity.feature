Feature: Activity Register #Jeppe

Scenario: Register to an activity as employee
  Given an activity with name "cook" and a project leader
  When an employee with UID "XX12" requests to join an activity
  Then the employee with UID "xx12" is added to the activity

Scenario: Register an employee for an activity as a project leader
  Given an activity with name "cook" and a project leader and free timeslots
  When the project leader with UID "XX20" registers an employee with UID "XX21" for the activity
  Then the employee with UID "XX21" is added to the activity

Scenario: Change activity date
  Given an activity with name "Build bench" and start date "week 10 year 2022" and end date "week 13 year 2022"
  When the project leader with UID "XX20" sets the dates to "week 11 year 2022" and "week 16 year 2022"
  Then the activity with name "Build bench" now has start date "week 11 year 2022" and end date "week 16 year 2022"

Scenario: Add employee with 20 activites to a new activity
  Given an activity with name "Build bench"
  When an employee with UID "XX02" who has 20 ongoing activites tries to join the activity with name "Build bench"
  Then the employee with UID "XX02" is not added to the activity

Scenario: Remove an employee from an activity
  Given an activity with name "Implementation" and an employee with UID "XX02"
  When the project leader with UID "XX20" removes the employee with UID "XX02" from the activity
  Then the employee with UID "XX02" is removed from the activity
  
Scenario: Edit previously registered work hours
  Given an activity with name "Testing" with 20 hours budgetted and 10 hours registered
  When an employee adds 1 hour to the registered work time
  Then the activity with name "Testing" now has 11 hours of registered time

Scenario: Exceed registered hours
  Given an activity with name "Testing" with 20 hours budgetted and 10 hours registered
  When an employee adds 15 hours to the registered work time
  Then the activity with name "Testing" returns an error message "Error: The added time exceeds the Budgetted time for the activity."

Scenario: Project leader wants to check budgetted hours and check if registered hours exceeds
  Given an activity with name "GetToWork" with 30 hours budgetted
  When the project leader checks the budgetted hours to be 30 hours
  And the project leader checks the registered hours to be 15 hours
  Then the project leader sees that the budgetted hours is 30 and that the registered hours is 15
  
Scenario: A project leader wants to set the max users for an activity
  Given an activity with name "Build bench" and a project leader
  When the project leader sets the max users to 5
  Then the activity with name "Build bench" now has max users set to 5

Scenario: A project leader wants to edit budgetted time and change start date and end date
  Given an activity with name "Build bench" and a project leader
  When the project leader want to add 5 hours to 20 hours budgetted time and changes the start date to "week 11 year 2022" and end date to "week 16 year 2022"
  Then the activity with name "Build bench" now has budgetted time set to 25 hours and start date "week 11 year 2022" and end date "week 16 year 2022"

Scenario: A project leader sets fixed activities
  Given an activity with name "Weekend" and a project leader
  When the project leader sets activity to be fixed
  Then the activity with name "Weekend" is now fixed