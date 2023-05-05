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
    
Scenario: Employee claims project manager position
    Given there is a project
    And employee with ID "mkr" is logged in
    And add employee with ID "mkr" to project
    And "mkr" claims position project manager
    Then "mkr" is project manager of project

Scenario: Employee unclaims project manager position
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    When "mkr" unclaims position project manager
    Then project has no project manager

Scenario: Employee tries to claims project manager position
  	Given admin is already logged in
    And there is a project
    And there is an employee with ID "thr"
    And add employee with ID "thr" to project
    When admin promotes "thr" to project manager
    Then "thr" is project manager of project
    Given admin logs out 
    And employee with ID "mkr" is logged in
    And "mkr" claims position project manager
    Then the error message "Project already has PM" is given