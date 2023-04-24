package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.DateServer;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProjectSteps {
	private ManagementSystemApp managementSystem;
	private ErrorMessageHolder errorMessageHolder;
	private DateServer dateServer;
	private Project project;
	
	private Calendar startDate;
	private Calendar endDate;
	
	public ProjectSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder, DateServer dateServer) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.dateServer = dateServer;
	}
	
	@Given("there is a project with name {string}")
	public void createProjectWithName(String projectName) throws OperationNotAllowedException {
		startDate = dateServer.getDate();
		endDate = dateServer.getDate();
		project = new Project(projectName, 0.0, startDate, endDate);
	}
	
	@When("add project to system")
	public void addProjectToSystem() throws OperationNotAllowedException {
		managementSystem.createProject(project);
	}
	
	@Then("the project is added to the system with unique project number")
	public void theProjectIsAddedToTheSystemWithUniqueProjectNumber() {
	    
	}

}
