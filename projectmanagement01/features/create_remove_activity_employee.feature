Feature: Create and remove activity as employee
    Description: Create and remove activity for project
    Actor: Employee
    
Scenario: Employee adds activity to project with no project manager then removes it
    Given there is a project
    And project has no project manager
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    When create activity with name "act1" for project
    Then activity "act1" for project is created
    When remove activity "act1" for project
    Then there is no activity named "act1"
    
Scenario: Employee attempts to add nameless activity to project with no project manager
    Given there is a project
    And project has no project manager
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    When create activity with name "" for project
    Then the error message "Activities must have a name" is given
    
Scenario: Employee attempts to add activity with same name as other activity
    Given there is a project
    And project has no project manager
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    And there is an activity "act2" in project
    When create activity with name "act2" for project
    Then the error message "Activities must have a unique name" is given
    
Scenario: Employee attempts to add activity to project with project manager
    Given there is a project
    And admin is already logged in
    And project has project manager
    And admin is logged out
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    When create activity with name "act1" for project
    Then the error message "Project Manager login required" is given
    
Scenario: Remove activity that does not exist
	Given there is a project
	And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
	When remove activity "Programming" for project
    Then the error message "Activity does not exist" is given

