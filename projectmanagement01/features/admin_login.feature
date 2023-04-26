Feature: Admin login
	Description: The administrator logs in and out of the projectmanagement system.
	Actor: Admin

Scenario: Admin login and logout
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