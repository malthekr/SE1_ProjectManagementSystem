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
	private List<Employee> employees = new ArrayList<>();
	
	public SearchSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
	}
	
	@When("Employee searches for {string} project")
	public void employeeSearchesForProject(String searchText) {
		projects = managementSystem.searchProject(searchText);
	}
	
	@Then("project named {string} appears with details")
	public void projectNamedAppears(String projectName) throws OperationNotAllowedException {
		boolean s = projectName.equals(projects.get(0).getProjectName());
		
		String p = managementSystem.projectDetails(projects.get(0));
		
	    assertTrue(s && (p != null));
	}
	
	@When("Employee searches for {int} project")
	public void employeeSearchesForProject(Integer projectId) {
		try {
			managementSystem.findProjectById(projectId);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
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
		activities = managementSystem.searchActivity((searchText));
	}

	@Then("activities named {string} and {string} appears")
	public void activitiesNamedAndAppears(String activityName1, String activityName2) throws OperationNotAllowedException {
		Activity a = managementSystem.findActivityByDescription(activities.get(0).getProjectId(), activityName1);
		Activity b = managementSystem.findActivityByDescription(activities.get(1).getProjectId(), activityName2);
		
		 assertTrue(a.toString().equals(activities.get(0).toString()) &&  b.toString().equals(activities.get(1).toString()));;
	}
	
	@When("Employee searches for {string} employees")
	public void employeeSearchesForEmployees(String searchText) {
	    employees = managementSystem.searchEmployee(searchText);
	}

	@Then("employee named {string} and {string} appears")
	public void employeeNamedAndAppears(String name1, String name2) {
		assertTrue(name1.equals(employees.get(0).getName()) && name2.equals(employees.get(1).getName()));
	}

	@Then("emlpoyee named {string} appears")
	public void emlpoyeeNamedAppears(String name) {
		assertTrue(name.equals(employees.get(0).getName()));
	}
}
