Feature: Project Leader

  Scenario: Add project leader to project with already existing project leader
    Given a Project with a project leader with UID "ELLE"
    When an employee with UID "HUBA" tries to become project leader 
    And Project leader with UID "ELLE" already exists on project
    Then the employee "HUBA" doesn't become project leader
