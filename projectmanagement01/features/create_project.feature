Feature: Create Project
    Description: Admin creates project
    Actor: Admin
   
Scenario: Create project
    Given admin is already logged in
    And there is a project with name "Management System"
    When add project to system
    Then the project is added to the system with unique project number
    
Scenario: Creates project without name
    Given admin is already logged in
    And create project
    When add project to system
    Then the project is added to the system with unique project number
   
Scenario: Creates project but admin is logged out
    Given admin is already logged in
    And create project
    And admin is logged out
    When add project to system
    Then the error message "Administrator login required" is given
   
Scenario: Create two projects
    Given admin is already logged in
    And there is a project with name "Management System"
    When add project to system
    Then the project is added to the system with unique project number
    And there is a project with name "Operating System"
    When add project to system
    Then the project is added to the system with unique project number
    
    
