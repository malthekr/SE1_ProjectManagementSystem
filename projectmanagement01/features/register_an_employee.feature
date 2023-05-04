Feature: Register an employee
  Description: Admin registers an employee
  Actor: Admin
  
Scenario: Register a new employee
	Given admin is already logged in
	And there is no employee with ID "mkr"
	When register an employee with Name "Malthe K. Reuber" and employee ID "mkr" 	
	Then the person is a registered employee of the system with ID "mkr"
	
Scenario: Register an employee with an already existing employee ID
	Given admin is already logged in
	And there is an employee with ID "mkr"
	When register an employee with Name "Malthe K. Reuber" and employee ID "mkr"
	Then the error message "Employee ID already taken" is given
	
Scenario: Register employee with no administrator logged in
	Given admin is not logged in
	When register an employee with Name "Malthe K. Reuber" and employee ID "mkr"
	Then the error message "Administrator login required" is given
	
Scenario: Register employee with too long ID
	Given admin is already logged in
	And there is no employee with ID "mkr"
	When register an employee with Name "Malthe K. Reuber" and employee ID "mkrnik"
	Then the error message "Employee ID is too long" is given