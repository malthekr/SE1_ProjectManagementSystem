Feature: Remove Project
    Description: Admin/employee/pm removes project
    Actor: Admin/employee/pm
   
Scenario: Remove project as admin
    Given admin is already logged in
    And there is a project with name "Management System"
    When add project to system
    Then the project is added to the system with unique project number
    When remove project from system
    Then there is no project named "Management System"
    
Scenario: Remove project as employee
		Given admin is already logged in
    Given there is a project with name "Management System"
    When add project to system
		And admin logs out
  	Given employee with ID "mkr" is logged in
    When remove project from system
		Then the error message "Admin or project manager login required" is given
	
Scenario: Remove project as project manager
		Given admin is already logged in
    Given there is a project with name "Management System"
    When add project to system
		And admin logs out
  	Given employee with ID "mkr" is logged in
  	And "mkr" is the project manager
  	And add employee with ID "mkr" to activity in project
    When remove project from system
    Then there is no project named "Management System"