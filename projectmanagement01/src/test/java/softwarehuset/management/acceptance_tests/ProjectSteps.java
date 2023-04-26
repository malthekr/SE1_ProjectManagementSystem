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
	private ProjectHelper projectHelper;
	
	private Calendar startDate;
	private Calendar endDate;
	
	public ProjectSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder, DateServer dateServer, ProjectHelper projectHelper) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.dateServer = dateServer;
		this.projectHelper = projectHelper;
	}
	
	@Given("there is a project")
	public void thereIsAProject() {
	    project = projectHelper.getProject();
	}
	
	@Given("there is a project with name {string}")
	public void createProjectWithName(String projectName) throws OperationNotAllowedException {
		project = projectHelper.getProject();
		project.editProjectName(projectName);
	}
	
	@When("add project to system")
	public void addProjectToSystem() throws OperationNotAllowedException {
		managementSystem.createProject(project);
	}
	
	@Then("the project is added to the system with unique project number")
	public void theProjectIsAddedToTheSystemWithUniqueProjectNumber() {
	    
	}

}
