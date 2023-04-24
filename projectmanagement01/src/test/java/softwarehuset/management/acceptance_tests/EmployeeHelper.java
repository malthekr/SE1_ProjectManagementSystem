package softwarehuset.management.acceptance_tests;

import softwarehuset.management.app.Employee;

public class EmployeeHelper {
	private Employee employee;
	
	public Employee getUser() {
		if (employee == null) {
			employee = exampleUser();
		}
		return employee;
	}
	
	private Employee exampleUser() {
		Employee employee = new Employee( "Malthe Reuber", "mkr");
		return employee;
	}

	public void setUser(Employee employeeInfo) {
		employee = employeeInfo;
	}
}