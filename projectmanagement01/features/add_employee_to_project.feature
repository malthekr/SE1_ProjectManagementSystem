Feature: Add employee to project
    Description: A project manager adds an employees to project
    Actor: Project Manager
    
# Main scenario
Scenario: Employee is added to the project
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee is the project manager
    And there is an employee with ID "thr"
    When add employee with ID "thr" to project
    Then another employee is added to project
    
# Alternative scenario
#Scenario: Employee that does not exist is added to the project:
    #Given there is a project
    #And employee with ID "mkr" is logged in
    #And employee is the project manager
    #And there is no employee with ID "thr"
    #When add employee with ID "thr" to project
    #Then the error message "Employee ID does not exist" is given