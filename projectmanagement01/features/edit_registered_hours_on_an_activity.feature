#Feature:: Edit registered hours on an activity
#			Description: An employee wants toedit his registered hours
#			Actor: Employee
#
#Scenario:
#		Given there is a project
#		And employee with ID "mkr" is logged in
#		And "mkr" is not the project manager
#		When set registered hours to "5"hours
#		Then the system edits the registered hours to "5"