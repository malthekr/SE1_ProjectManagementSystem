package softwarehuset.management.acceptance_tests;

import softwarehuset.management.app.Employee;

public class EmployeeHelper {
	private Employee employee;
	
	public Employee getEmployee() {
		if (employee == null) {
			employee = exampleEmployee();
		}
		return employee;
	}
	
	private Employee exampleEmployee() {
		Employee employee = new Employee( "Niklas Reuber", "mok");
		return employee;
	}

	public void setEmploeye(Employee employeeInfo) {
		employee = employeeInfo;
	}
}