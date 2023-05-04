Feature: edit expected hours for project
    tion aboutDescription: Add information about expected hours regarding a project.
    Actor: Project Manager
    
Scenario: edit expected hours to a project as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    When set expected project hours to 100.0
    Then expected project hours is 100.0
    
Scenario: Regular employee attempts to edit expected hours to a project
    Given there is a project
    And employee with ID "mkr" is logged in
    And add employee with ID "mkr" to project
    When set expected project hours to 100.0
    Then expected project hours is 100.0

Scenario: Regular employee attempts to edit expected hours to a project with project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And add employee with ID "mkr" to project
    And there is an employee with ID "thr"
    And "thr" is the project manager
    When set expected project hours to 100.0
    Then the error message "Project Manager login required" is given
 
Scenario: Regular employee logged out and tries to edit expected hours of project
    Given there is a project
    And employee with ID "mkr" is logged in
    And add employee with ID "mkr" to project
    And employee "mkr" is logged out
    When set expected project hours to 100.0
    Then the error message "Project Manager login required" is given
 