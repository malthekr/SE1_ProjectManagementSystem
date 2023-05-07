Feature: Edit activity as employee
    Description: Edit activity for project
    Actor: Employee
    
Scenario: Employee edits activity for project with no project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    And there is an activity "act1" in project
    When set start date to 10-05-2023 for activity "act1"
    Then start date for activity "act1" is set to 10-05-2023
    

Scenario: Employee attempts to edit activity for project with project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is an activity "act1" in project
    And employee is logged out
    Given employee with ID "nik" is logged in
    And employee "nik" is part of project
    When set start date to 10-05-2024 for activity "act1"
    Then the error message "Project Manager login required" is given
