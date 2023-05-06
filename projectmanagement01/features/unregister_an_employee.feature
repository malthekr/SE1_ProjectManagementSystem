Feature: Unregister an employee
    Description: Admin unregisters an employee
    Actor: Admin

Scenario: Unregister an employee
    Given admin is already logged in
    And there is an employee with ID "mkr"
    When unregister the employee with ID "mkr"
    Then the employee is unregistered from the system
 
Scenario: Unregister an employee that does not exist
    Given admin is already logged in
    And there is no employee with ID "mkr"
    When unregister the employee with ID "mkr"
    Then the error message "Employee ID does not exist" is given

Scenario: Unregister an employee that does not exist
    Given employee with ID "mkr" is logged in
    And there is an employee with ID "thr"
    When unregister the employee with ID "thr"
    Then the error message "Administrator login required" is given

Scenario: Unregister an employee that is part of a project and activity
    Given admin is already logged in
   	And there is an employee with ID "mkr"
    And there is a project
    And employee "mkr" is part of project
  	And add employee with ID "mkr" to activity in project
    When unregister the employee with ID "mkr"
    Then the employee is unregistered from the system
    Then employee "mkr" is not part of the project
    And employee "mkr" is not part of the activity "NewActivity20"
   