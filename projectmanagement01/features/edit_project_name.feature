Feature: Edit project name
    Description: A project manager edits details on project
    Actor: Project Manager
    
Scenario: Project manager edits project name
		Given admin is already logged in
		And there is a project with name "Management System"
		When add project to system
    Then the project is added to the system with unique project number
    Given employee with ID "mkr" is logged in
    And  admin is logged out
    And "mkr" is the project manager
    When "mkr" edits project name to "Alpha Management System"
    Then the project name is "Alpha Management System"
    
Scenario: Employee edits project name
		Given admin is already logged in
		And there is a project with name "Management System"
		When add project to system
    Then the project is added to the system with unique project number
		Given employee with ID "mkr" is logged in
		And  admin is logged out
    When "mkr" edits project name to "Alpha Management System"
    Then the error message "Employee has to be Project Manager to change project name" is given

Scenario: Employee edits project name while not logged in
		Given admin is already logged in
		And there is a project with name "Management System"
		When add project to system
    Then the project is added to the system with unique project number
    Given there is an employee with ID "mkr"
    And admin is logged out
    When "mkr" edits project name to "Alpha Management System"
    Then the error message "Project Manager log in required" is given    
    #Skal admin også kunne ændre navnet på projektet?