Feature: Create Project
    Description: Admin creates project
    Actor: Admin
    
Scenario: Create project
    Given admin is already logged in
    And there is a project with name "Management System"
    When add project to system
    Then the project is added to the system with unique project number
    
#Scenario: Creates project without name
    #Given that the admin is logged in
    #When create project
    #Then the project is added to the system with unique project number