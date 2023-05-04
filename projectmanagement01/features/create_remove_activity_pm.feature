Feature: Create aemnd orve activity as project manager
    Description: Create activity for project
    Actor: Project Manager

Background: Employee logs in and is project manager
	Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager

Scenario: Create activity as project manager
    When creates activity "Programming" for project
    Then activity "Programming" for project is created
    When remove activity "Programming" for project
    Then there is no activity named "Programming"

Scenario: Remove activity that does not exist
    When remove activity "Programming" for project
    Then the error message "Activity does not exist" is given
    
Scenario: Create activity that already exists
    When creates activity "Programming" for project
    Then activity "Programming" for project is created
    When creates activity "Programming" for project
    Then the error message "Activities must have a unique name" is given