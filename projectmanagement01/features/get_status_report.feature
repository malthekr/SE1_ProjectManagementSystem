Feature: Get status report
    Description: Get status report containing remaining expected workload per activity and for the whole project.
    Actor: Project Manager
    
Main scenario
Scenario: Get status report as project manager
    Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
    And employee with ID "mkr" has 10 ongoing activities
    And there is an employee with ID "thr"    
    And employee with ID "thr" has 6 ongoing activities
  	When add employee with ID "thr" to activity in project
  	Then employee with ID "thr" is added to the project activity
    When request status report for project
    Then system provides status report for project
    
# Alternative scenarios
#Scenario: Get status report as employee
    #Given there is a project
    #And employee with ID "mkr" is logged in
    #And "mkr" is not the project manager
    #And employee is part of project
    #When request status report for project
    #Then the error message "Project Manager required" is given