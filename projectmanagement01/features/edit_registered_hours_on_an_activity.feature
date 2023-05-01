Feature:: Edit registered hours on an activity
			Description: An employee wants to edit his registered hours
			Actor: Employee

Scenario: Employee adds hours to an activity
		Given there is a project
		And employee with ID "mkr" is logged in
    And employee "mkr" is part of project
		And add employee with ID "mkr" to activity in project
		When add registered hours to 5.4 hours to activity
		Then the system edits the registered hours to 5.0
