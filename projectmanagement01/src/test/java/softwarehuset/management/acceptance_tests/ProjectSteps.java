package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
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

public class ProjectSteps {
	private ManagementSystemApp managementSystem;
	private ErrorMessageHolder errorMessageHolder;
	private Project project;
	private ProjectHelper projectHelper;
	private EmployeeHelper employeeHelper;
	
	public ProjectSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder, ProjectHelper projectHelper, EmployeeHelper employeeHelper) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.projectHelper = projectHelper;
		this.employeeHelper = employeeHelper;
	}
	
	@Given("there is a project with name {string}")
	public void createProjectWithName(String projectName) throws OperationNotAllowedException {
		project = projectHelper.getProject();
		project.editProjectName(projectName);
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
	public void createProject() throws OperationNotAllowedException {
		project = projectHelper.getProject();
	}
	
	@Then("the project is added to the system with unique project number")
	public void theProjectIsAddedToTheSystemWithUniqueProjectNumber() {
		assertTrue(managementSystem.checkIfUniqueProjectId(project.getProjectID()));
	}
	
	@Given("there is a project")
	public void thereIsAProject() throws OperationNotAllowedException {	    
		managementSystem.adminLogin("admi");
		project = projectHelper.getProject();
		addProjectToSystem();
	    managementSystem.adminLogout();
	}
	
	@Given("there is a new project")
	public void thereIsANewProject() throws OperationNotAllowedException {
		project = projectHelper.getProject();
	    addProjectToSystem();
	}
	
	@Given("{string} is the project manager")
	public void isTheProjectManager(String EmployeeId) throws OperationNotAllowedException {
		project.addEmployee(managementSystem.FindEmployeeById(EmployeeId));
		managementSystem.promoteToPm(project.getProjectID(), EmployeeId);
		assertEquals(project.getProjectManager(), managementSystem.FindEmployeeById(EmployeeId));
	}
	
	@Given("employee is not the project manager")
	public void isNotTheProjectManager() throws OperationNotAllowedException {
		Employee projectManager = managementSystem.currentEmployee();
		assertFalse(project.getProjectManager() == projectManager);
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

	@When("edits project name to {string}")
	public void editsProjectNameTo(String projectName) throws OperationNotAllowedException {
		try {
			managementSystem.editProjectName(project.getProjectID(), projectName);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@When("edits start date by {int} days")
	public void editsStartDateByDays(int days) {
		try {
			managementSystem.editStartDate(project.getProjectID(), days);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@When("edits end date by {int} days")
	public void editsEndDateByDays(int days) {
		try {
			managementSystem.editEndDate(project.getProjectID(), days);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("project starts {int} days later")
	public void projectStartsDaysLater(int days) throws OperationNotAllowedException {
		assertTrue(managementSystem.CheckifStartDateMoved(project.getProjectID(), days, project.getStartDate()));
	}
	
	@Then("project ends {int} days later")
	public void projectEndsDaysLater(int days) throws OperationNotAllowedException {
		assertTrue(managementSystem.CheckifEndDateMoved(project.getProjectID(), days, project.getEndDate()));
	}
	
	@When("creates activity {string} for project")
	public void createsActivityForProject(String description) {
		try {
			managementSystem.createActivity(project.getProjectID(), description);
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
	
	@When("close project")
	public void closeProject() {
		try {
			managementSystem.closeProject(project);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("project is closed")
	public void projectIsClosed() {
	   assertFalse(project.getOngoingProject());
	}
	
	
	private void createEmployee(String id) throws OperationNotAllowedException {
		Employee employee = new Employee(id);
		managementSystem.adminLogin("admi");
		managementSystem.addEmployee(employee);
		managementSystem.adminLogout();
	}
	
	@Given("employee {string} is part of project")
	public void employeeIsPartOfProject(String id) throws OperationNotAllowedException {
		Employee employee = managementSystem.FindEmployeeById(id);
		project.addEmployee(employee);
	}
	
	@When("create activity with name {string} for project")
	public void createActivityWithNameForProject(String activityName) throws OperationNotAllowedException {
		try {
			managementSystem.createActivity(project.getProjectID(), activityName);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Given("there is an activity {string} in project")
	public void thereIsAnActivityInProject(String activityName) throws OperationNotAllowedException {
		createActivityWithNameForProject(activityName);
	}
	
	@When("edit expected hours for project to {double}")
	public void editExpectedHoursForProjectTo(Double newExpectedHours) {
	    project.editExpectedHours(newExpectedHours);
	}
	
	@Then("expected hours for project is {double}")
	public void expectedHoursForProjectIs(double expectedHours) {
		assertEquals(project.getExpectedHours(), expectedHours, 0);
	}
	
	@Given("there are {int} projects added to the system")
	public void thereAreProjectsAddedToTheSystem(Integer numberOfProjects) throws OperationNotAllowedException {
	    for(int i=0; i<numberOfProjects; i++) {
	    	project = projectHelper.getProject(Integer.toString(i));
			managementSystem.createProject(project);
	    }
	}
	
	@Given("employee with ID {string} has {int} ongoing activities")
	public void employeeWithIDHasOngoingActivities(String id, int ongoingActvities) throws OperationNotAllowedException {
	    Employee employee = managementSystem.FindEmployeeById(id);
	    
	    for(int i = 0; i <= ongoingActvities; i++) {
	    	String description = "activity"+i;
	    	createActivityWithNameForProject(description);
	    	managementSystem.addEmployeeToActivity(employee, project, description);
	    	Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
	    	activity.setExpectedHours(i);
	    	//employee.addActivity(project, activity);
	    }
	}

	@When("add employee with ID {string} to activity in project")
	public void addEmployeeWithIDToActivityInProject(String id) throws OperationNotAllowedException {
		try {
			//Employee employee = managementSystem.FindEmployeeById(id);
			String description = "NewActivity20";
	    	createActivityWithNameForProject(description);
	    	managementSystem.addEmployeeToActivity(managementSystem.FindEmployeeById(id), project, description);
	    	//Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
			
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("employee with ID {string} is added to the project activity")
	public void employeeWithIDIsAddedToTheProjectActivity(String id) throws OperationNotAllowedException  {
			String description = "NewActivity20";
			Employee employee = managementSystem.FindEmployeeById(id);
			Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
			//employee.addActivity(project, activity);
			//employee.isPartOfActivity(project, activity);
			assertEquals(activity.getEmployees().contains(employee), employee.getActivities().contains(activity));
	}
	
	@When("set expected project hours to {double}")
	public void setExpectedProjectHoursTo(double hours) throws OperationNotAllowedException {
		try {
			managementSystem.UpdateExpectedHours(project.getProjectID(), hours);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("expected project hours is {double}")
	public void expectedProjectHoursIs(double hours) {
		assertEquals(project.getExpectedHours(), hours, 0);
	}
	
	@When("set start date to {int}-{int}-{int} for activity {string}")
	public void setStartDateToForActivity(int dd, int mm, int yyyy, String description) {
	    try {
			managementSystem.UpdateStartDate(dd, mm, yyyy, project.getProjectID(), description);
			
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("start date for activity {string} is set to {int}-{int}-{int}")
	public void startDateForActivityIsSetTo(String description, int dd, int mm, int yyyy) {
		try {
			Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
			Calendar calendar = new GregorianCalendar();
			Calendar newDate = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
			newDate = managementSystem.setDate(newDate, dd, mm, yyyy);
			
		    assertEquals(activity.getStartDate(), newDate);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@When("edits description of activity {string} to {string}")
	public void editsDescriptionOfActivityTo(String description1, String description2) {
	   try {
		   managementSystem.setActivityDescrption(project, description1, description2);
	   } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
	   }
	}
	
	@Then("activity description is {string}")
	public void activityDescriptionIs(String description) throws OperationNotAllowedException  {
		Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
		assertEquals(activity.getDescription(), description);
	}
	
	@When("request status report for project")
	public void requestStatusReportForProject() throws OperationNotAllowedException {
		managementSystem.generateStatusReport(project.getProjectID());
	}
	
	@When("set registered hours to {int} hours")
	public void setRegisteredHoursToHours(int hours) {
		
		
	}
	
	@When("request employee activity of {string}")
	public List<Activity> requestEmployeeActivityOfAnotherEmployee(String Id) throws OperationNotAllowedException {
		Employee employee = managementSystem.currentEmployee();
		Employee anotherEmployee = managementSystem.FindEmployeeById(Id);
		assertNotEquals(employee, anotherEmployee);
		return anotherEmployee.getActivities();
	}

	@Then("a timetable of activity from {string} is given")
	public List<Activity> aTimetableOfActivity(String Id) throws OperationNotAllowedException {
		Employee employee = managementSystem.FindEmployeeById(Id);
		return employee.getActivities();
	}
	
}
