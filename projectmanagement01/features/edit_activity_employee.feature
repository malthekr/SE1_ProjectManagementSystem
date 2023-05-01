Feature: Edit activity
    Description: Edit activity for project
    Actor: Employee
    
Scenario: Employee edits activity for project with no project manager
    Given there is a project
    #And project has no project manager
    And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
    And there is an activity "act1" in project
    When set start date to "10-05-2023" for activity "act1"
    Then start date for activity "act1" is set to "10-05-2023"
    
# Main scenario
#Scenario: Employee attempts to edit activity for project with project manager
    #Given there is a project
    #And project has project manager
    #And project has activity "Act1"
    #Given employee with ID "mkr" is logged in
    #And employee is part of project
    #And employee is not project manager
    #When set start date to "10-05-2023" for activity
    #Then error message "Only Project Manager is allowed to edit activities" is given