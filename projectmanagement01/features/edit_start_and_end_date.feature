Feature: Edit project start and stop date
    Description: A project manager edits details on project
    Actor: Project Manager

Scenario: Project manager edits start date of project
		Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
   	When edits start date by 5 days
   	Then project starts 5 days later
   	
Scenario: Project manager edits end date of project
		Given there is a project
    And employee with ID "mkr" is logged in
    And "mkr" is the project manager
   	When edits end date by 5 days
   	Then project ends 5 days later  	
   	
Scenario: Employee edits start date of project
   	Given there is a project
    And employee with ID "mkr" is logged in
   	When edits start date by 5 days
   	Then the error message "Project Manager login required" is given

Scenario: Employee edits end date of project
   	Given there is a project
    And employee with ID "mkr" is logged in
   	When edits end date by 5 days
   	Then the error message "Project Manager login required" is given   	

Scenario: Employee logged out and edits start date of project
   	Given there is a project
    And employee with ID "mkr" is logged in
   	When edits start date by 5 days
   	Then the error message "Project Manager login required" is given

Scenario: Employee edits start date of project
   	Given there is a project
   	And admin is already logged in
    And there is an employee with ID "mkr"
    And admin is logged out
   	When edits start date by 5 days
   	Then the error message "Project Manager login required" is given  

Scenario: Employee edits start date of project
   	Given there is a project
   	And admin is already logged in
    And there is an employee with ID "mkr"
    And admin is logged out
   	When edits end date by 5 days
   	Then the error message "Project Manager login required" is given  

