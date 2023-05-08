package softwarehuset.management.acceptance_tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwarehuset.management.app.*;
import softwarehuset.management.app.OperationNotAllowedException;

import java.util.List;

import org.junit.Assert.*;

public class RegisterHoursSteps {
	private ManagementSystemApp managementSystem;
	//private EmployeeHelper employeeHelper;
	private ErrorMessageHolder errorMessageHolder;
	private LoginSystem loginSystem;
	private EmployeeRepository employeeRepository;
	
	private Employee employee;
	 
	public RegisterHoursSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.loginSystem = managementSystem.getLoginSystem();
		this.employeeRepository = managementSystem.getEmployeeRepository();
	}
	
	@When("view registered hours")
	public List<Activity> viewRegisteredHours() throws OperationNotAllowedException {
		Employee employee = employeeRepository.findEmployeeByID(loginSystem.getCurrentLoggedID());
		return employee.getActivities();
	}

	@Then("the system provides the registered hours")
	public void theSystemProvidesTheRegisteredHours() throws OperationNotAllowedException {
	    
	}

}
