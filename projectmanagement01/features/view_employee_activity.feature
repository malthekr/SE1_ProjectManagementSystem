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
    
Scenario: View employee activity as employee
    Given there is a project
    And employee with ID "nik" exists
    And "nik" is the project manager
    And employee with ID "mkr" is logged in
    And employee is not the project manager
    And employee with ID "thr" exists
    When request employee activity of "thr"
    Then the error message "Project Manager login required" is given