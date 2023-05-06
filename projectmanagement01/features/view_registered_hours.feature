Feature: View registered hours
    Description: An employee wants to view his registered hours for a specific date
    Actor: Employee
    
#Scenario: PM wants to see project status
#    Given there is a project
#    And employee with ID "mkr" is logged in
#    When view registered hours
#    Then the system provides the registered hours    
    
Scenario:
    Given there is a project
    And employee with ID "mkr" is logged in
    When view registered hours
    Then the system provides the registered hours