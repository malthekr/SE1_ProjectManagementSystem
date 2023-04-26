Feature: Admin login
	Description: The administrator logs in and out of the projectmanagement system.
	Actor: Admin

Scenario: Administrator login and logout
	Given that the administrator is not logged in
	And the admin id is "admi"
	Then the administrator login succeeds
	And the adminstrator is logged in
	When the administrator logs out
	Then the administrator is not logged in
	
Scenario: Admin login with wrong id
	Given admin is not logged in
	And admin id is "fail"
	Then admin login fails
	And admin is not logged in
	
Scenario: Employee login
	Given admin is already logged in
	And there is an employee with ID "mkr"
	And admin is logged out
	When "mkr" logs in
	Then employee is logged in
	
Scenario: Employee login but not registered
		Given admin is already logged in
		And there is an employee with ID "mkr"
		And admin is logged out
		When "mpr" logs in
		Then the error message "Employee ID does not exist" is given
		
