Feature: Remove employee from project
  	Description: A project manager wants to remove an employee from a project
    Actor: Project Manager
    
# Main scenario
Scenario: Project manager removes employee
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is an employee with ID "mkr" working on the project
    When remove employee "mkr" from project
    Then employee "mkr" is not part of the project
    
Scenario: Employee not found
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And employee "mkr" is not part of the project
    When remove employee "mkr" from project
    Then the error message "Employee is not part of project" is given