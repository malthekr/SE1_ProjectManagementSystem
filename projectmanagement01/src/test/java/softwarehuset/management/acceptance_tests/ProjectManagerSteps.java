package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.Activity;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProjectManagerSteps {
	private ManagementSystemApp managementSystemApp;
	private Activity activity;
	private Employee employee;
	private ErrorMessageHolder errorMessageHolder;
	private Project project;

	private Calendar startDate;
	private Calendar endDate;

	public ProjectManagerSteps(ManagementSystemApp managementSystemApp, Project project, Activity activity,
			Employee employee, ErrorMessageHolder errorMessageHolder) {
		this.managementSystemApp = managementSystemApp;
		this.errorMessageHolder = errorMessageHolder;
		this.project = project;
		this.employee = employee;
		this.activity = activity;
	}

	@Given("employee with ID {string} is the project manager")
	public void employeeIsTheProjectManager(String id) throws OperationNotAllowedException {
		Employee employee = managementSystemApp.FindEmployeeById(id);
		project.addEmployee(employee);
		managementSystemApp.promoteToPm(project.getProjectID(), id);
		assertEquals(project.getProjectManager(), employee);
	}

	@Given("employee with ID {string} has {int} ongoing activities")
	public void employeeWithIDHasOngoingActivities(String id, int number) throws OperationNotAllowedException {
		Employee employee1 = managementSystemApp.FindEmployeeById(id);
		Project project = managementSystemApp.createProject("pro1");
		for (int i = 1; i <= number; i++) {
			String name = "act" + i;
			managementSystemApp.createActivity(number, name);
			managementSystemApp.joinActivity(number, name);
			Activity act = managementSystemApp.findActivityByDescription(number, name);
			employee1.addActivity(act);
		}
		assertEquals(employee1.getNumOfActivities(),number);
	}

	@When("add employee with ID {string} to activity in project")
	public void addEmployeeWithIDToActivityInProject(String string) throws OperationNotAllowedException {
		
	}

	@Then("employee with ID {string} is added to the project activity")
	public void employeeWithIDIsAddedToTheProjectActivity(String string) throws OperationNotAllowedException {
		
	}
}
