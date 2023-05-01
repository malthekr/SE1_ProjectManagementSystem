package softwarehuset.management.acceptance_tests;

import org.junit.jupiter.api.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwarehuset.management.app.Activity;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;

import java.util.List;

import org.junit.Assert.*;

public class RegisterHoursSteps {
	private ManagementSystemApp managementSystem;
	//private EmployeeHelper employeeHelper;
	private ErrorMessageHolder errorMessageHolder;
	
	private Employee employee;
	 
	public RegisterHoursSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
	}
	
	@When("view registered hours")
	public List<Activity> viewRegisteredHours() throws OperationNotAllowedException {
		Employee employee = managementSystem.currentEmployee();
		return employee.getActivities();
	}

	@Then("the system provides the registered hours")
	public void theSystemProvidesTheRegisteredHours() throws OperationNotAllowedException {
	    
	}

}
