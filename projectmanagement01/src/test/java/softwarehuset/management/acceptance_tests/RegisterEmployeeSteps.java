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
import softwarehuset.management.app.DateServer;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.OperationNotAllowedException;


public class RegisterEmployeeSteps {
	private ManagementSystemApp managementSystemApp;
	private EmployeeHelper employeeHelper;
	private ErrorMessageHolder errorMessageHolder;
	
	private Employee employee;
	
	public RegisterEmployeeSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder, Employee employee , EmployeeHelper employeeHelper) {
		this.managementSystemApp = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.employeeHelper = employeeHelper;
//		this.employee = employee;
	}
	
	@Given("there is no employee with ID {string}")
	public void thereIsNoEmployeeWithID(String id) throws Exception {
		assertTrue(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@Given("there is an employee with ID {string}")
	public void thereIsAnEmployeeWithID(String id) throws Exception {
		employee = new Employee(id);
		addEmployee(employee);
		assertTrue(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@When("register an employee with Name {string} and employee ID {string}")
	public void registerAnEmployeeWithNameAndEmployeeID(String Name, String id) throws Exception{
		employee = new Employee(Name, id);
		addEmployee(employee);
	}
	
	@Then("the person is an registered employee of the system with ID {string}")
	public void thePersonIsAnRegisteredEmployeeOfTheSystemWithId(String id){
		assertTrue(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@Then("the error message {string} is")
	public void theErrorMessageIs(String errorMessage){
		assertThat(errorMessageHolder.getErrorMessage(), is(equalTo(errorMessage)));
	}
	
	private void addEmployee(Employee employee) throws OperationNotAllowedException{
		managementSystemApp.addEmployee(employee);
	}
}
