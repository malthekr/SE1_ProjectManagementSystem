Feature: Admin login
	Description: The administrator logs in and out of the projectmanagement system.
	Actor: Admin

Scenario: Administrator login and logout
	Given admin is not logged in
	And admin id is "admi"
	Then admin login succeeds
	And admin is logged in
	When admin logs out
	Then admin is not logged in

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

Scenario: Employee login but not registered
		Given admin is already logged in
		And there is an employee with ID "mkr"
		And admin is logged out
		When "mpr" logs in
		Then the error message "Employee ID does not exist" is given