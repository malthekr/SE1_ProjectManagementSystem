Feature: Create activity as project manager
    Description: Create activity for project
    Actor: Project Manager
    
Scenario: Create activity as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    When creates activity "Programming" for project
    Then activity "Programming" for project is created