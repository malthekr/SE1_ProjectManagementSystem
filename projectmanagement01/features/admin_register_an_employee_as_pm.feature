Feature: Admin register an employee as project manager
    Description: Admin register an employee as project manager
    Actor: Admin
    
Scenario: Register employee as project manager
    Given admin is already logged in
    And there is an employee with ID "mkr"
    And there is a new project
    And add employee with ID "mkr" to project
    And project has no project manager
    When admin promotes "mkr" to project manager
    Then "mkr" is project manager of project
    
Scenario: Project manager already exists
    Given admin is already logged in
    And there is an employee with ID "mkr"
    And there is a new project
    And add employee with ID "mkr" to project
    And project has project manager
    When admin promotes "mkr" to project manager
    Then the error message "Project already has Project Manager" is given

Scenario: Employee is not part of project
    Given admin is already logged in
    And there is an employee with ID "mkr"
    And there is a new project
    And employee "mkr" is not part of the project
    When admin promotes "mkr" to project manager
    Then the error message "Employee is not part of the project" is given