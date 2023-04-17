Feature: Create activity
    Description: Create activity for project
    Actor: Employee
    
# Main scenario
#Scenario: Employee adds activity to project with no project manager
    #Given there is a project
    #And project has no project manager
    #And employee with ID "mkr" is logged in
    #And employee is part of project
    #And employee is not project manager
    #When create activity with name "act1" for project
    #Then activity is added to project
    #
# Alternative scenario
#Scenario: Employee attempts to add nameless activity to project with no project manager
    #Given there is a project
    #And project has no project manager
    #And employee with ID "mkr" is logged in
    #And employee is part of project
    #And employee is not project manager
    #When create activity with name "" for project
    #Then error message "Activities must have a name" is given
    #
# Alternative scenario
#Scenario: Employee attempts to add activity with same name as other activity
    #Given there is a project
    #And project has no project manager
    #And employee with ID "mkr" is logged in
    #And employee is part of project
    #And employee is not project manager
    #And there is an activity "act2" in project
    #When create activity with name "act2" for project
    #Then error message "Activities must have a unique name" is given
    #
# Alternative scenario
#Scenario: Employee attempts to add activity to project with project manager
    #Given there is a project
    #And project has project manager
    #And employee with ID "mkr" is logged in
    #And employee is part of project
    #And employee is not project manager
    #When create activity with name "act1" for project
    #Then error message "Only Project Manager can add activities" is given