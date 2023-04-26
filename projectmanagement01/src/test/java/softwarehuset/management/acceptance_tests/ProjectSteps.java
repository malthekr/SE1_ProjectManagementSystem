package softwarehuset.management.acceptance_tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.Activity;
import softwarehuset.management.app.DateServer;
import softwarehuset.management.app.Employee;
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
		 
		int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
		int Id = managementSystem.generateID(year);
		project = new Project(projectName, 0.0, startDate, endDate, Id);
	}
	
	@When("add project to system")
	public void addProjectToSystem() throws OperationNotAllowedException {
		try {
			managementSystem.createProject(project);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		} 
	}
	
	@Given("create project")
	public void createProject() {
		startDate = dateServer.getDate();
		endDate = dateServer.getDate();
		int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
		int Id = managementSystem.generateID(year);
		project = new Project(0.0, startDate, endDate, Id);
	}
	
	@Then("the project is added to the system with unique project number")
	public void theProjectIsAddedToTheSystemWithUniqueProjectNumber() {
		assertTrue(managementSystem.checkIfUniqueProjectId(project.getProjectID()));
	}
	
	@Given("there is a project")
	public void thereIsAProject() throws OperationNotAllowedException {
		managementSystem.adminLogin("admi");
	    exampleProject();
	    addProjectToSystem();
	    managementSystem.adminLogout();
	}
	
	@Given("there is a new project")
	public void thereIsANewProject() throws OperationNotAllowedException {
		exampleProject();
	    addProjectToSystem();
	}
	
	@Given("{string} is the project manager")
	public void isTheProjectManager(String EmployeeId) throws OperationNotAllowedException {
		project.addEmployee(managementSystem.FindEmployeeById(EmployeeId));
		managementSystem.promoteToPm(project.getProjectID(), EmployeeId);
		assertEquals(project.getProjectManager(), managementSystem.FindEmployeeById(EmployeeId));
	}
	
	@Then("{string} is project manager of project")
	public void isProjectManagerOfProject(String employeeId) throws OperationNotAllowedException {
		assertEquals(project.getProjectManager(), managementSystem.FindEmployeeById(employeeId));
	}
	
	@When("add employee with ID {string} to project")
	public void addEmployeeWithIDToProject(String EmployeeId) {
		try {
			managementSystem.addEmployeeToProject(project.getProjectID(), EmployeeId);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		} 
	}

	@Then("employee {string} is added to project")
	public void employeeIsAddedToProject(String EmployeeId) throws OperationNotAllowedException {
		assertTrue(managementSystem.checkIfEmployeeIsPartOfProject(project.getProjectID(), EmployeeId));
	}
	
	@Given("there is also an employee with ID {string} part of project")
	public void thereIsAlsoAnEmployeeWithIDPartOfProject(String id) throws OperationNotAllowedException {
		createEmployee(id);
		addEmployeeWithIDToProject(id);
	}
	
	@When("remove employee {string} from project")
	public void removeEmployeeFromProject(String employeeId) {
		try {
			managementSystem.removeEmployeeWithIdFromProject(project.getProjectID(), employeeId);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}		
	}
	
	@Then("employee {string} is not part of the project")
	public void employeeIsNotPartOfTheProject(String EmployeeId) throws OperationNotAllowedException {
		assertFalse(managementSystem.checkIfEmployeeIsPartOfProject(project.getProjectID(), EmployeeId));
	}
	
	@Given("project has no project manager")
	public void projectHasNoProjectManager() throws OperationNotAllowedException {
	    assertFalse(managementSystem.checkIfProjectHasPM(project.getProjectID()));
	}
	
	@Given("project has project manager")
	public void projectHasProjectManager() throws Exception {
		String id = "nik";
		Employee employee = new Employee(id);
		managementSystem.addEmployee(employee);
		addEmployeeWithIDToProject(id);
		managementSystem.promoteToPm(project.getProjectID(), id);
	}
	
	@When("admin promotes {string} to project manager")
	public void adminPromotesToProjectManager(String employeeId) {
		try {
			managementSystem.promoteToPm(project.getProjectID(), employeeId);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@When("{string} edits project name to {string}")
	public void editsProjectNameTo(String employeeId, String projectName) throws OperationNotAllowedException {
		try {
			managementSystem.editProjectName(project.getProjectID(), employeeId, projectName);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@When("{string} edits start date by {int} days")
	public void editsStartDateByDays(String employeeId, int days) {
		try {
			managementSystem.editStartDate(project.getProjectID(), employeeId, days);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@When("{string} edits end date by {int} days")
	public void editsEndDateByDays(String employeeId, int days) {
		try {
			managementSystem.editEndDate(project.getProjectID(), employeeId, days);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("project starts {int} days later")
	public void projectStartsDaysLater(int days) throws OperationNotAllowedException {
		assertTrue(managementSystem.CheckifStartDateMoved(project.getProjectID(), days, startDate));
	}
	
	@Then("project ends {int} days later")
	public void projectEndsDaysLater(int days) throws OperationNotAllowedException {
		assertTrue(managementSystem.CheckifEndDateMoved(project.getProjectID(), days, endDate));
	}
	
	@When("{string} creates activity {string} for project")
	public void createsActivityForProject(String employeeId, String description) {
		try {
			managementSystem.createActivity(project.getProjectID(), employeeId, description);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("activity {string} for project is created")
	public void activityForProjectIsCreated(String description) throws OperationNotAllowedException {
		Activity a = managementSystem.findActivityByDescription(project.getProjectID(), description);
		assertEquals(a.getDescription(), description);
	}
	
	@Then("the project name is {string}")
	public void theProjectNameIs(String projectName) {
		assertEquals(project.getProjectName(), projectName);
	}
	
	//Mangler
	@When("close project")
	public void closeProject() {
		try {
			managementSystem.closeProject(project);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	//Mangler
	@Then("project is closed")
	public void projectIsClosed() {
	   assertFalse(project.getOngoingProject());
	}
	
	private void exampleProject() {
		startDate = dateServer.getDate();
		endDate = dateServer.getDate();
		int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
		int Id = managementSystem.generateID(year);
		project = new Project(0.0, startDate, endDate, Id);
	}
	
	private void createEmployee(String id) throws OperationNotAllowedException {
		Employee employee = new Employee(id);
		managementSystem.adminLogin("admi");
		managementSystem.addEmployee(employee);
		managementSystem.adminLogout();
	}
}
