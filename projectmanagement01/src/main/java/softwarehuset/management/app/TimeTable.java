package softwarehuset.management.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import softwarehuset.management.app.Employee;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;
import softwarehuset.management.app.Activity;
import softwarehuset.management.app.HoursWorked;

public class TimeTable {

	private Employee employee;
	private ManagementSystemApp managementSystem;
	private Project project;
	private String emp = "mkr";
	
	@Given("employee is part of project")
	public void employeeIsPartOfProject() throws OperationNotAllowedException {
		assertTrue(managementSystem.checkIfEmployeeIsPartOfProject(project.getProjectID(), emp));  
	    //throw new io.cucumber.java.PendingException();
	}
	
	@Given("project has the activity {string}")
	public void projectHasTheActivity(String activity) throws OperationNotAllowedException {
		Activity a = managementSystem.findActivityByDescription(project.getProjectID(), activity);
	    assertEquals(a.getDescription(), activity);
		//throw new io.cucumber.java.PendingException();
	}
	
	@When("add {string} hours to activity in project")
	public void addHoursToActivityInProject(String hours) {
	    String b = hours;
	    //throw new io.cucumber.java.PendingException();
	}
	
	@Then("{string} hours is added to activity in project")
	public void hoursIsAddedToActivityInProject(String string) {
		
	    // Write code here that turns the phrase above into concrete actions
	    //throw new io.cucumber.java.PendingException();
	}

	
	
	
}
