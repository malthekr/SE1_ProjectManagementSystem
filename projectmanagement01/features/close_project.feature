Feature: Close project
    Description: Close a project.
    Actor: Project Manager
    # Main scenario
    
Scenario: Close a project as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    When close project
    Then project is closed