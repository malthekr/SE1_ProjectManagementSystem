package softwarehuset.management.acceptance_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.DateServer;
import softwarehuset.management.app.Employee;


public class RegisterEmployeeSteps {
	private ManagementSystemApp managementSystemApp;
	private EmployeeHelper employeeHelper;
	private ErrorMessageHolder errorMessageHolder;
	
	private Employee employee;
	
	public RegisterEmployeeSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder, EmployeeHelper employeeHelper) {
		this.managementSystemApp = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.employeeHelper = employeeHelper;
//		this.employee = employee;
	}
	
	@Given("there is no employee with ID {string}")
	public void thereIsNoEmployeeWithID(String id) throws Exception {
		assertFalse(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@Given("there is an employee with ID {string}")
	public void thereIsAnEmployeeWithID(String id) throws Exception {
		employee = new Employee(id);
		addEmployee(employee);
		assertTrue(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@When("register an employee with Name {string} and employee ID {string}")
	public void registerAnEmployeeWithNameAndEmployeeID(String name, String id) throws Exception{
		employee = new Employee(name, id);
		addEmployee(employee);
	}
	
	@Then("the person is an registered employee of the system with ID {string}")
	public void thePersonIsAnRegisteredEmployeeOfTheSystemWithId(String id){
		assertTrue(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@Then("the error message {string} is given")
	public void theErrorMessageIs(String errorMessage){
		assertThat(errorMessageHolder.getErrorMessage(), is(equalTo(errorMessage)));
	}
	
	private void addEmployee(Employee employee) throws Exception{
		try {
			managementSystemApp.addEmployee(employee);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
}
