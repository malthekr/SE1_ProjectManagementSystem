package softwarehuset.management.acceptance_tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import org.junit.Assert.*;


public class RegisterEmployeeSteps {
	private ManagementSystemApp managementSystemApp;
	private ErrorMessageHolder errorMessageHolder;
	
	private Employee employee;
	 
	public RegisterEmployeeSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder) {
		this.managementSystemApp = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
	}
	
	@Given("there is no employee with ID {string}")
	public void thereIsNoEmployeeWithID(String id) throws OperationNotAllowedException {
		assertFalse(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@Given("there is an employee with ID {string}")
	public void thereIsAnEmployeeWithID(String id) throws OperationNotAllowedException {
		employee = new Employee(id);
		if (managementSystemApp.adminLoggedIn()) {
			addEmployee(employee);
		}
		else {
			managementSystemApp.adminLogin("admi");
			addEmployee(employee);
			managementSystemApp.adminLogout();
		}
		assertTrue(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@When("register an employee with Name {string} and employee ID {string}")
	public void registerAnEmployeeWithNameAndEmployeeID(String name, String id) throws OperationNotAllowedException{
		employee = new Employee(name, id);
		addEmployee(employee);
	}
	
	@Then("the person is a registered employee of the system with ID {string}")
	public void thePersonIsAnRegisteredEmployeeOfTheSystemWithId(String id){
		assertTrue(managementSystemApp.containsEmployeeWithId(id));	
	}
	
	@Then("the error message {string} is given")
	public void theErrorMessageIs(String errorMessage){
		assertEquals(errorMessage, errorMessageHolder.getErrorMessage());
	}
	
	@When("unregister the employee with ID {string}")
	public void unregisterTheEmployeeWithID(String id) {
		try {
			employee = managementSystemApp.FindEmployeeById(id);
		    managementSystemApp.removeEmployee(employee);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("the employee is unregistered from the system")
	public void theEmployeeIsUnregisteredFromTheSystem() {
	    assertFalse(managementSystemApp.containsEmployeeWithId(employee.getId()));	
	}

	private void addEmployee(Employee employee){
		try {
			managementSystemApp.addEmployee(employee);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
}
