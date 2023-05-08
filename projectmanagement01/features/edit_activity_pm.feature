Feature: Edit activity as project manager
    Description: Edit activity for project
    Actor: Project Manager

Scenario: Edit activity as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is an activity "act1" in project
    When edits description of activity "act1" to "act2"
    Then activity description is "act2"
    
Scenario: PM edits start date activity for project
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is an activity "act1" in project
    When set start date to 10-05-2022 for activity "act1"
    Then start date for activity "act1" is set to 10-05-2022

Scenario: PM edits end date activity for project
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is an activity "act1" in project
    When set end date to 10-05-2024 for activity "act1"
    Then end date for activity "act1" is set to 10-05-2024

Scenario: PM edits expected hour activity for project
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is an activity "act1" in project
    When edits expected hour to 10.6 for activity "act1"
    Then expected hour for activity "act1" is 10.5

Scenario: PM edits activity description for project
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And there is an activity "act1" in project
    When edits activity description to "act2" for activity "act1"
    Then description of activity is "act2"