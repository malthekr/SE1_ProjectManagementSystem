Feature: Create Project
    Description: Admin creates project
    Actor: Admin
# Main scenario
Scenario: Create project
    Given that the admin is logged in
    When create project with name "Management System"
    Then the project is added to the system with unique project number
# Alternative scenario
Scenario: Creates project without name
    Given that the admin is logged in
    When create project
    Then the project is added to the system with unique project number