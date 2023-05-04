package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.Activity;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchSteps {
	private ManagementSystemApp managementSystem;
	private ErrorMessageHolder errorMessageHolder;
	private List<Project> projects = new ArrayList<>();
	private List<Activity> activities = new ArrayList<>();
	
	public SearchSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
	}
	
	@When("Employee searches for {string} project")
	public void employeeSearchesForProject(String searchText) {
		projects = managementSystem.searchProject(searchText);
		//System.out.println("length: "+projects.size());
		//System.out.println(managementSystem.getProjectList().size());
	}
	
	@Then("project named {string} appears")
	public void projectNamedAppears(String projectName) {
		//System.out.println(projects.get(0).getProjectName());
	    assertTrue(projectName.equals(projects.get(0).getProjectName()));
	}
		
	@Then("project named {string} and {string} appears")
	public void projectNamedAndAppears(String projectName1, String projectName2) {
		assertTrue(projectName1.equals(projects.get(0).getProjectName()) && projectName2.equals(projects.get(1).getProjectName()));
	}

	@Then("activity named {string} appears")
	public void activityNamedAppears(String activityName) {
	    assertTrue(activityName.equals(activities.get(0).getDescription()));
	}

	@When("Employee searches for {string} activities")
	public void employeeSearchesForActivities(String searchText) {
		activities = managementSystem.searchActivity(searchText);
	}

	@Then("activities named {string} and {string} appears")
	public void activitiesNamedAndAppears(String activityName1, String activityName2) {
		 assertTrue(activityName1.equals(activities.get(0).getDescription()) && activityName2.equals(activities.get(1).getDescription()));
	}
	
}
