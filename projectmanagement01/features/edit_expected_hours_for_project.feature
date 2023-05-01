Feature: Edit expected hours for project
    Description: Edit information about expected hours regarding a project.
    Actor: Project Manager
    
Scenario: Edit expected hours for a project as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    When edit expected hours for project to 60
    Then expected hours for project is 60