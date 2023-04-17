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
	
Scenario: Administrator login with wrong id
	Given that the administrator is not logged in
	And the admin id is "fail"
	Then the administrator login fails
	And the administrator is not logged in