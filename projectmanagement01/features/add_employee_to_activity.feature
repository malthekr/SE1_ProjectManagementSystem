Feature: Add employee to activity
    Description: The project manager adds an employee to an activity
    Actor: Project Manager
    
# Main scenario
#Scenario: Add employee to activity
  #Given there is a project
  #And employee with ID "mkr" is logged in
  #And employee is the project manager
  #And there is an employee with ID "thr"
  #And employee with ID "thr" has "19" ongoing activities
  #When add employee with ID "thr" to activity in project
  #Then employee with ID "thr" is added to the project activity
#
# Alternative scenario
#Scenario: Busy employee is added to acitivy
#	Given there is a project
  #And employee with ID "mkr" is logged in
  #And employee is the project manager
  #And there is an employee with ID "thr"
  #And employee with ID "thr" has "20" ongoing activities
  #When add employee with ID "thr" to activity in project
  #Then the error message "Employee too busy" is given