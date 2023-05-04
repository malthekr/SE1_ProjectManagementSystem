Feature: Remove employee from project
  	Description: A project manager wants to remove an employee from a project
    Actor: Project Manager
    
Scenario: Project manager removes employee
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is also an employee with ID "thr" part of project
    When remove employee "thr" from project
    Then employee "thr" is not part of the project
    
Scenario: Employee not found
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is an employee with ID "thr"
    And employee "thr" is not part of the project
    When remove employee "thr" from project
    Then the error message "Employee is not part of project" is given