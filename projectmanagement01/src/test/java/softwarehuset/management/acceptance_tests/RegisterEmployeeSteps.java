package softwarehuset.management.acceptance_tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwarehuset.management.app.*;
import org.junit.Assert.*;


public class RegisterEmployeeSteps {
	private ManagementSystemApp managementSystemApp;
	private ErrorMessageHolder errorMessageHolder;
	private LoginSystem loginSystem;
	private EmployeeRepository employeeRepository;
	
	private Employee employee;
	 
	public RegisterEmployeeSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder) {
		this.managementSystemApp = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.loginSystem = managementSystem.getLoginSystem();
		this.employeeRepository = managementSystem.getEmployeeRepository();
	}
	
	@Given("there is no employee with ID {string}")
	public void thereIsNoEmployeeWithID(String id) throws OperationNotAllowedException {
		assertFalse(employeeRepository.checkIfEmployeeExists(id));	
	}
	
	@Given("there is an employee with ID {string}")
	public void thereIsAnEmployeeWithID(String id) throws OperationNotAllowedException {
		employee = new Employee(id);
		if (loginSystem.adminLoggedIn()) {
			addEmployee(employee);
		}
		else {
			loginSystem.adminLogin("admi");
			addEmployee(employee);
			loginSystem.adminLogout();
		}
		assertTrue(employeeRepository.checkIfEmployeeExists(id));	
	}
	
	@When("register an employee with Name {string} and employee ID {string}")
	public void registerAnEmployeeWithNameAndEmployeeID(String name, String id) throws OperationNotAllowedException{
		employee = new Employee(name, id);
		addEmployee(employee);
	}
	
	@Then("the person is a registered employee of the system with ID {string}")
	public void thePersonIsAnRegisteredEmployeeOfTheSystemWithId(String id){
		assertTrue(employeeRepository.checkIfEmployeeExists(id));	
	}
	
	@Then("the error message {string} is given")
	public void theErrorMessageIs(String errorMessage){
		assertEquals(errorMessage, errorMessageHolder.getErrorMessage());
	}
	
	@When("unregister the employee with ID {string}")
	public void unregisterTheEmployeeWithID(String id) {
		try {
			employee = employeeRepository.findEmployeeByID(id);
		    managementSystemApp.removeEmployee(employee);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("the employee is unregistered from the system")
	public void theEmployeeIsUnregisteredFromTheSystem() {
	    assertFalse(employeeRepository.checkIfEmployeeExists(employee.getId()));	
	    
	}

	private void addEmployee(Employee employee){
		try {
			employeeRepository.addEmployee(employee);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
}
