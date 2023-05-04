Feature: Add expected hours for project
    tion aboutDescription: Add information about expected hours regarding a project.
    Actor: Project Manager
    
Scenario: Add expected hours to a project as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    When set expected project hours to 100.0
    Then expected project hours is 100.0
    
Scenario: Regular employee attempts to add expected hours to a project
    Given there is a project
    And employee with ID "mkr" is logged in
    And add employee with ID "mkr" to project
    When set expected project hours to 100.0
    Then the error message "Project Manager login required" is given