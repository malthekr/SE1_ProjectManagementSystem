Feature: Add employee to project
    Description: A project manager adds an employees to project
    Actor: Project Manager
    
Scenario: Employee is added to the project by project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is also an employee with ID "thr"
    When add employee with ID "thr" to project
    Then employee "thr" is added to project
    
Scenario: Employee already part of project is added to the project by project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is also an employee with ID "thr"
    When add employee with ID "thr" to project
    Then employee "thr" is added to project
    When add employee with ID "thr" to project
    Then the error message "Employee already part of project" is given
    
Scenario: Employee that does not exist is added to the project:
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is no employee with ID "thr"
    When add employee with ID "thr" to project
    Then the error message "Employee ID does not exist" is given

Scenario: Employee is added to the project by employee
    Given there is a project
    And employee with ID "mkr" is logged in
    And there is also an employee with ID "thr"
    When add employee with ID "thr" to project
    Then the error message "Employee has to be Project Manager to add employees" is given
    
Scenario: Employee is added to the project but no one is logged in
    Given there is a project
    And employee with ID "mkr" is logged in
    And there is also an employee with ID "thr"
    And employee "mkr" is logged out
    When add employee with ID "thr" to project
    Then the error message "Admin or Project Manager log in required" is given

