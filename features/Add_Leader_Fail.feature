Feature: Project Leader //Frank

  Scenario: Add project "projectTest" leader to project with already existing project leader
    Given a Project with a project leader with UID "ELLE"
    Given An app
    When an employee with UID "HUBA" tries to become project leader 
    And Project leader with UID "ELLE" already exists on project
    Then the employee with UID "HUBA" is not project leader


