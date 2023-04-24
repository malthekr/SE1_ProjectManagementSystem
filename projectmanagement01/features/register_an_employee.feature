Feature: Register an employee
  Description: Admin registers an employee
  Actor: Admin
  
Scenario: Register a new employee
	Given that the admin is logged in
	And there is no employee with ID "mkr"
	When register an employee with Name "Malthe K. Reuber" and employee ID "mkr" 	
	Then the person is an registered employee of the system with ID "mkr"
	
#Scenario: Register an employee with an already existing employee ID
#	Given that the admin is logged in
#	And there is an employee with ID "mkr"
#	When register an employee with Name "Malthe K. Reuber" and employee ID "mkr"
#	Then the error message "Employee ID already taken" is