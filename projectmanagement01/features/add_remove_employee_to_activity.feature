Feature: Add/remove employee to/from activity
    Description: The project manager adds an employee to an activity
    Actor: Project Manager
    
Scenario: Add employee to activity
  Given there is a project
  And employee with ID "mkr" is logged in
  And "mkr" is the project manager
  And there is also an employee with ID "thr" part of project
  And employee with ID "thr" has 19 ongoing activities
  When add employee with ID "thr" to activity in project
  Then employee with ID "thr" is added to the project activity
  
Scenario: Busy employee is added to activity
  Given there is a project
  And employee with ID "mkr" is logged in
  And "mkr" is the project manager
  And there is also an employee with ID "thr" part of project
  And there is an employee with ID "thr"
  And employee with ID "thr" has 20 ongoing activities
  When add employee with ID "thr" to activity in project
  Then the error message "Employee too busy" is given
  
Scenario: add employee to activity but not part of project
	Given admin is already logged in
	And register an employee with Name "niklas" and employee ID "nik"
	And register an employee with Name "malthe" and employee ID "mkr"
	When there is a project with name "Management System"
	And add project to system
	And "nik" is the project manager
	And add employee with ID "mkr" to activity in project
	Then the error message "Employee not part of project" is given
  
Scenario: Remove employee from activity
	Given there is a project
	And employee with ID "nik" is logged in
	And "nik" is the project manager
	And add employee with ID "nik" to activity in project
	When remove "nik" from activity
	Then "nik" is removed from activity

Scenario: Remove employee that is not part of activity from activity
	Given admin is already logged in
	And register an employee with Name "niklas" and employee ID "nik"
	And register an employee with Name "malthe" and employee ID "mkr"
	When there is a project with name "Management System"
	And add project to system
	And "nik" is the project manager
	And add employee with ID "nik" to activity in project
	When remove "mkr" from activity
	Then the error message "Employee not part of project" is given

	
  
  