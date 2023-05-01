Feature: Add expected hours for project
    tion aboutDescription: Add information about expected hours regarding a project.
    Actor: Project Manager
    
# Main scenario
Scenario: Add expected hours to a project as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee is project manager
    When set expected project hours to "100" for project
    Then expected project hours for project is set to "100"
    
# Alternative scenario
Scenario: Regular employee attempts to add expected hours to a project
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee is not project manager
    When set expected project hours to "100" for project
    Then error message "Only Project Managers are allowed to set expected hours" is given