Feature: Edit project
    Description: A project manager edits details on project
    Actor: Project Manager and Employee
    
Scenario: Project manager edits project name
		Given admin is already logged in
		And there is a project with name "Management System"
		When add project to system
    Then the project is added to the system with unique project number
    Given employee with ID "mkr" is logged in
    And  admin is logged out
    And "mkr" is the project manager
    When edits project name to "Alpha Management System"
    Then the project name is "Alpha Management System"
    
Scenario: Employee edits project name
		Given admin is already logged in
		And there is a project with name "Management System"
		When add project to system
    Then the project is added to the system with unique project number
		Given employee with ID "mkr" is logged in
		And  admin is logged out
    When edits project name to "Alpha Management System"
    Then the error message "Project Manager login required" is given

Scenario: Employee edits project name while not logged in
		Given admin is already logged in
		And there is a project with name "Management System"
		When add project to system
    Then the project is added to the system with unique project number
    Given there is an employee with ID "mkr"
    And admin is logged out
    When edits project name to "Alpha Management System"
    Then the error message "Project Manager login required" is given    
    
Scenario: PM edits start date of project
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    When set start date to 10-05-2023 for project
    Then start date project is set to 10-05-2023

Scenario: PM edits end date of project
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    When set end date to 10-05-2024
    Then end date for project is set to 10-05-2024