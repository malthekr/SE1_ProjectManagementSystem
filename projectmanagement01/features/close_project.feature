Feature: Close project
    Description: Close a project.
    Actor: Project Manager
     Main scenario
    
Scenario: Close project as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And project is active
    When close project
    Then project is closed
    
Scenario: Close project but project manager is logged out
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And employee is logged out 
    When close project
    Then the error message "Project Manager login required" is given