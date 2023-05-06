Feature: View employee activity
    Description: The project manager checks the employee activity.
    Actor: Project Manager
    
Scenario: View employee activity as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And employee with ID "thr" exists
    When request employee activity of "thr"
    Then a timetable of activity from "thr" is given
    
Scenario: View status of employee with no active projects
    Given employee with ID "mkr" is logged in
    When there is a project with name "Programming"
    And "mkr" is the project manager
    And employee with ID "thr" exists
    When request active status of employee "thr"
    Then status of employee from "thr" is printed

Scenario: View status of employee with an active projects
		Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And project is active
    And add employee with ID "mkr" to activity in project
    When add 5.99 hours to activity "NewActivity20" in project
    And request active status of employee "mkr"
    Then status of employee from "mkr" is printed
    
Scenario: View employee activity as employee
    Given there is a project
    And employee with ID "nik" exists
    And "nik" is the project manager
    And employee with ID "mkr" is logged in
    And employee is not the project manager
    And employee with ID "thr" exists
    When request employee activity of "thr"
    Then the error message "Project Manager login required" is given

    