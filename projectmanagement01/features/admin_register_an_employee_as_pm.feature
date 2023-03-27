Feature: Admin register an employee as project manager
    Description: Admin register an employee as project manager
    Actor: Admin
    
# Main scenario
Scenario: Register employee as project manager
    Given that the admin is logged in
    And there is an employee with ID "mkr"
    And there is a project
    And employee is part of project
    And project has no project manager
    When admin promotes "mkr" to project manager
    Then "mkr" is project manager of project
    
# Alternative scenario
Scenario: Project manager already exists
    Given that the admin is logged in
    And there is an employee with ID "mkr"
    And there is a project
    And employee is part of project
    And project has project manager
    When admin promotes "mkr" to project manager
    Then error message "Project Manager already assigned" is given

# Alternative scenario
Scenario: Employee is not part of project
    Given that the admin is logged in
    And there is an employee with ID "mkr"
    And there is a project
    And employee is not part of project
    When admin promotes "mkr" to project manager
    Then error message "Employee is not part of the project" is given