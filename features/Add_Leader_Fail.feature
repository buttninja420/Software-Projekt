Feature: Project Leader #Frank

  Scenario: Add project "projectTest" leader to project with already existing project leader
    Given a Project "testproject" with a project leader with UID "ELLE"
    When an employee with UID "HUBA" tries to become project leader 
    Then the employee with UID "HUBA" is not project leader


