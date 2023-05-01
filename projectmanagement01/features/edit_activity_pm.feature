Feature: Edit activity
    Description: Edit activity for project
    Actor: Project Manager

Scenario: Edit activity as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is an activity "act1" in project
    When edits description of activity "act1" to "act2"
    Then activity description is "act2"
    
Scenario: PM edits activity for project
    Given there is a project
    And employee with ID "mkr" is logged in
    #And employee "mkr" is part of project
    And "mkr" is the project manager
    And there is an activity "act1" in project
    When set start date to 10-05-2023 for activity "act1"
    Then start date for activity "act1" is set to 10-05-2023