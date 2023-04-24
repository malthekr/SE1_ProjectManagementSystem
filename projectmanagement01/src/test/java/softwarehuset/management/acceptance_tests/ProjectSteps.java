package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

class ProjectSteps {
	
	private ManagementSystemApp managementSystem;
	private ErrorMessageHolder errorMessageHolder;
	private MockDateHolder dateHolder;
	
	public ProjectSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder, MockDateHolder dateHolder) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.dateHolder = dateHolder;
	}

	@When("create project with name {string}")
	public void createProjectWithName(String projectName) throws Exception {
		project = new Project(projectName, 0.0, dateHolder, dateHolder);
		
		try {
			managementSystem.addProject(project);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	

}
