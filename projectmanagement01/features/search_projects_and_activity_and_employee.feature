Feature: Search projects, activities and employees
    Description: Employee searches for projects and activities
    Actor: Employee

Background: Employee login
		Given admin is already logged in
		When register an employee with Name "Malthe K. Reuber" and employee ID "mkr"
    And register an employee with Name "niklas" and employee ID "nik"
    And register an employee with Name "hans" and employee ID "hans"
    And register an employee with Name "thor" and employee ID "thr"
    And register an employee with Name "chad" and employee ID "chad"
  	When there is a project with name "Management System"
  	And add project to system
  	And "nik" is the project manager
  	And there is an activity "sedel" in project
  	And add employee with ID "nik" to activity in project
    When there is a project with name "Programming"
    And add project to system
    And add employee with ID "thr" to project
    And there is an activity "del1" in project
    When there is a project with name "Example Project 2"
    And add project to system
    And "thr" is the project manager
    When there is a project with name "GUI"
    And add project to system
    And "nik" is the project manager
    And add employee with ID "nik" to activity in project
    When admin logs out

Scenario: Employee searches for projects name
    When Employee searches for "Programming" project
    Then project named "Programming" appears with details
    
Scenario: Employee searchs after all projects with certain PM id
    When Employee searches for "nik" project
    Then project named "Management System" and "GUI" appears
    
Scenario: Employee searchs after all projects with certain PM name
		When Employee searches for "niklas" project
    Then project named "Management System" and "GUI" appears

Scenario: Employee searchs after projects with project id
		When Employee searches for 22001 project
    Then the error message "Project Id does not exist" is given
    
#Scenario: Employee searchs after all activities with project id
#		When Employee searches for "23075" activities
#    Then activity named "del1" appears
    
Scenario: Employee searchs after all activities with employee id
		When Employee searches for "nik" activities
    Then activities named "NewActivity20" and "NewActivity20" appears

Scenario: Employee searchs after all activities with employee name
		When Employee searches for "niklas" activities
    Then activities named "NewActivity20" and "NewActivity20" appears

Scenario: Employee searchs after all activities with name
		When Employee searches for "sedel" activities
    Then activity named "sedel" appears with details

Scenario: Employee searchs after all employees with name
		When Employee searches for "n" employees
    Then employee named "niklas" and "hans" appears
    
Scenario: Employee searchs after all employees with id
		When Employee searches for "chad" employees
    Then emlpoyee named "chad" appears
    