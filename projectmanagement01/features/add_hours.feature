Feature: Add hours
    Description: Add hours worked on an activity in a project
    Actor: Employee
    
Scenario: Employee adds worked hours to an activity
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    And there is an activity "act1" in project
    When add 5.99 hours to activity "act1" in project
    Then 5.5 hours is added to activity "act1" in project
    
Scenario: Employee attempts to add worked hours to an activity that does not exist
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    When add 5.99 hours to activity "act1" in project
    Then the error message "Activity does not exist" is given