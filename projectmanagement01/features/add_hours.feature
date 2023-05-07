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
    
Scenario: Employee adds worked hours to an activity but is not logged in
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    And there is an activity "act1" in project
    And employee is logged out
    When add 5.99 hours to activity "act1" in project
    Then the error message "Employee login required" is given
    
Scenario: Employee attempts to add worked hours to an activity that does not exist
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    When add 5.99 hours to activity "act1" in project
    Then the error message "Activity does not exist" is given
    
Scenario: Employee adds worked hours to an activity already worked on
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    And there is an activity "act1" in project
    When add 5.99 hours to activity "act1" in project
    Then 5.5 hours is added to activity "act1" in project
    When add 10.2 hours to activity "act1" in project
    Then 15.5 hours is added to activity "act1" in project
    
Scenario: Employee adds negative worked hours to an activity
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    And there is an activity "act1" in project
    When add 5.99 hours to activity "act1" in project
    Then 5.5 hours is added to activity "act1" in project
    When add -4.4 hours to activity "act1" in project
    Then 1.5 hours is added to activity "act1" in project
    
Scenario: Employee edits worked hours to an activity
    Given there is a project
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    And add employee with ID "mkr" to activity in project
    When add 5.99 hours to activity "NewActivity20" in project
    Then worked 5.5 hours in activity "NewActivity20" in project
    When edit hour to 3.4 hours is added as worked hour to activity "NewActivity20" in project
    Then 3.0 hours is added as worked hour to activity "NewActivity20" in project 
    