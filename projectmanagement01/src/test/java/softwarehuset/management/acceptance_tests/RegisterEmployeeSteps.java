package softwarehuset.management.acceptance_tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.OperationNotAllowedException;
import java.util.ArrayList;
import java.util.List;


class RegisterEmployeeSteps {
	
	private ManagementSystemApp managementSystemApp;
	private List<Employee> Employees;
	
	@Given("there is no employee with ID {string}")
	public void thereIsNoEmployeeWithID(String id) throws Exception {
		assertTrue(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@Given("there is an employee with ID {string}")
	public void thereIsAnEmployeeWithID(String id) throws Exception {
		AddEmployee(id);
		assertTrue(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	
	private void AddEmployee(String id){
		
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
