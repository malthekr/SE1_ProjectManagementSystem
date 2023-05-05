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
		projectHelper = new ProjectHelper();
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
	
	@When("remove project from system")
	public void whenRemoveProjectFromSystem() throws OperationNotAllowedException {
		try {
			managementSystem.removeProject(project);
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
	
	@Then("there is no project named {string}")
	public void thereIsNoProjectNamed(String projectName) {
		List<Project> projects = managementSystem.getProjectList();
		boolean flag = false;
		for(Project p : projects){
			if(p.getProjectName() == projectName){
				flag = true;
			}
		}
		assertFalse(flag);
		//assertTrue(managementSystem.checkIfUniqueProjectId(project.getProjectID()));
	}
	
	@Given("there is a project")
	public void thereIsAProject() throws OperationNotAllowedException {	    
		if(managementSystem.adminLoggedIn()) {
			project = projectHelper.getProject();
			addProjectToSystem();
		} else {
			managementSystem.adminLogin("admi");
			project = projectHelper.getProject();
			addProjectToSystem();
		    managementSystem.adminLogout();
		}
	}
	
	@Given("there is a new project")
	public void thereIsANewProject() throws OperationNotAllowedException {
		project = projectHelper.getProject();
	    addProjectToSystem();
	}
	
	@Given("{string} is the project manager")
	public void isTheProjectManager(String EmployeeId) {
		try {
			project.addEmployee(managementSystem.FindEmployeeById(EmployeeId));
			managementSystem.promoteToPm(project.getProjectID(), EmployeeId);
			assertEquals(project.getProjectManager(), managementSystem.FindEmployeeById(EmployeeId));
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		} 
		
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
	
	@When("set start date to {int}-{int}-{int} for project")
	public void setStartDateToForProject(int dd, int mm, int yyyy) throws OperationNotAllowedException {
		managementSystem.UpdateStartDateProject(dd, mm, yyyy, project.getProjectID());
	}

	@Then("start date project is set to {int}-{int}-{int}")
	public void startDateProjectIsSetTo(int dd, int mm, int yyyy) {
		Calendar calendar = new GregorianCalendar();
		Calendar newDate = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		newDate = managementSystem.setDate(newDate, dd, mm, yyyy);
		
	    assertEquals(project.getStartDate(), newDate);
	}

	@When("set end date to {int}-{int}-{int}")
	public void setEndDateTo(int dd, int mm, int yyyy) throws OperationNotAllowedException {
		managementSystem.UpdateEndDateProject(dd, mm, yyyy, project.getProjectID());
	}

	@Then("end date for project is set to {int}-{int}-{int}")
	public void endDateForProjectIsSetTo(int dd, int mm, int yyyy) {
		Calendar calendar = new GregorianCalendar();
		Calendar newDate = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		newDate = managementSystem.setDate(newDate, dd, mm, yyyy);
		
	    assertEquals(project.getEndDate(), newDate);
	}
	
	/*
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
	*/
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
	
	@Then("employee {string} is not part of the activity {string}")
	public void employeeIsNotPartOfTheActivity(String id, String description) throws OperationNotAllowedException {
		Activity a = managementSystem.findActivityByDescription(project.getProjectID(), description);
		List<Employee> employees = a.getEmployees();
		
		boolean flag = false;
		for(Employee e : employees){
			if(e.getId().equals(id)){
				flag = true;
				break;
			}
		}
		assertFalse(flag);
		//Employee employee = managementSystem.FindEmployeeById(id);
		//assertFalse(a.getEmployees().contains(employee));
	}
	
	@Then("the project name is {string}")
	public void theProjectNameIs(String projectName) {
		assertEquals(project.getProjectName(), projectName);
	}
	
	@Given("project is active")
	public void projectIsActive() {
	   closeProject();
	}
	
	@When("close project")
	public void closeProject() {
		try {
			managementSystem.toggleProjectOngoing(project);
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
		assertEquals(project.getExpectedHours(), expectedHours, 0.0);
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
	    
	    if(!project.findEmployee(employee))
			managementSystem.addEmployeeToProject(project.getProjectID(), id);
	    
	    for(int i = 0; i <= ongoingActvities; i++) {
	    	String description = "activity"+i;
	    	createActivityWithNameForProject(description);
	    	Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
	    	managementSystem.addEmployeeToActivity(employee, project, description);
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
	
	@When("remove {string} from activity")
	public void removeFromActivity(String id) {
	    try {
	    	//Employee employee = managementSystem.FindEmployeeById(id);
			String description = "NewActivity20";
	    	managementSystem.removeEmployeeFromActivity(managementSystem.FindEmployeeById(id), project, description);
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
	
	@Then("{string} is removed from activity")
	public void isRemovedFromActivity(String id) throws OperationNotAllowedException {
		String description = "NewActivity20";
		Employee employee = managementSystem.FindEmployeeById(id);
		Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
		assertFalse(activity.getEmployees().contains(employee));
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
	public void startDateForActivityIsSetTo(String description, int dd, int mm, int yyyy) throws OperationNotAllowedException {
			Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
			Calendar calendar = new GregorianCalendar();
			Calendar newDate = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
			newDate = managementSystem.setDate(newDate, dd, mm, yyyy);
			
		    assertEquals(activity.getStartDate(), newDate);
	}
	
	@When("set end date to {int}-{int}-{int} for activity {string}")
	public void setEndDateToForActivity(int dd, int mm, int yyyy, String description) throws OperationNotAllowedException {
		managementSystem.UpdateEndDate(dd, mm, yyyy, project.getProjectID(), description);
	}

	@Then("end date for activity {string} is set to {int}-{int}-{int}")
	public void endDateForActivityIsSetTo(String description, int dd, int mm, int yyyy) throws OperationNotAllowedException {
		Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
		Calendar calendar = new GregorianCalendar();
		Calendar newDate = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		newDate = managementSystem.setDate(newDate, dd, mm, yyyy);
		
	    assertEquals(activity.getEndDate(), newDate);
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

	@When("add {double} hours to activity {string} in project")
	public void addHoursToActivityInProject(double hours, String description) throws OperationNotAllowedException {
		try{
			//managementSystem.findActivityByDescription(project.getProjectID(), description).addWorkedHours(hours);
			Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
			managementSystem.addHourToActivity(activity, hours);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
	   }
//		Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
//		activity.addWorkedHours(hours);
		//managementSystem.findActivityByDescription(project.getProjectID(), description).addWorkedHours(hours);
		
	}

	@Then("{double} hours is added to activity {string} in project")
	public void hoursIsAddedToActivityInProject(double hours, String description) throws OperationNotAllowedException {
		assertEquals(hours, managementSystem.findActivityByDescription(project.getProjectID(), description).getWorkedHours(), 0);
	}
	
	@When("request employee activity of {string}")
	public List<Activity> requestEmployeeActivityOfAnotherEmployee(String id) {	
		try {
			Employee employee = managementSystem.currentEmployee();
			Employee anotherEmployee = managementSystem.FindEmployeeById(id);
			assertNotEquals(employee, anotherEmployee);
			return managementSystem.getActivities(project.getProjectID(), anotherEmployee);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
			return null;
		}
	}

	@Then("a timetable of activity from {string} is given")
	public List<Activity> aTimetableOfActivity(String id) throws OperationNotAllowedException {
		Employee employee = managementSystem.FindEmployeeById(id);
		return employee.getActivities();
	}
	
	@When("request active status of employee {string}")
	public void requestActiveStatusOfEmployee(String id) throws OperationNotAllowedException {
		Employee employee = managementSystem.FindEmployeeById(id);
		String s = managementSystem.getStatusOfEmployee(employee, false);
		assertTrue(s != null);
	}

	@Then("status of employee from {string} with no active {string}")
	public void statusOfEmployeeFromWithNoActive(String id, String status) throws OperationNotAllowedException {
		Employee employee = managementSystem.FindEmployeeById(id);
		//Since no active project we can expect message
		String statusEmployee = managementSystem.getStatusOfEmployee(employee, false).replace("<html><br><b>", "").replace("</b></html>", "");
	    assertEquals(statusEmployee, status);
	}
	
	@Then("status of employee from {string} is printed")
	public void statusOfEmployeeFromIsPrinted(String id) throws OperationNotAllowedException {
		Employee employee = managementSystem.FindEmployeeById(id);
	
		
		assertTrue(managementSystem.getStatusOfEmployee(employee, true) != null);
	}
	
	@When("add registered hours to {double} hours to activity")
	public void addRegisteredHoursToHoursToActivity(double hours) {
		try {
			String description = "NewActivity20";
			project.findActivityByDescription(description).addWorkedHours(hours);	
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}	
	}
	
	@Then("the system edits the registered hours to {double}")
	public void theSystemEditsTheRegisteredHoursTo(double hours) throws OperationNotAllowedException {
		String description = "NewActivity20";
		Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
		assertEquals(activity.getWorkedHours(), hours, 0);
	}
	
	@When("request status report for project")
	public void requestStatusReportForProject() throws OperationNotAllowedException {
		String s = managementSystem.getStatReportOfProject(project);
		assertTrue(s != null);
		//managementSystem.generateStatusReport(project.getProjectID());
	}
	
	@Then("system provides status report for project")
	public void systemProvidesStatusReportForProject() throws OperationNotAllowedException {
		assertTrue(managementSystem.getStatReportOfProject(project) != null);
	}
	
	@When("remove activity {string} for project")
	public void removeActivityForProject(String activityName) {
		try {
			Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), activityName);
			managementSystem.removeActivity(activity);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}	
	}
	
	@Then("there is no activity named {string}")
	public void thereIsNoActivityNamed(String activityName) throws OperationNotAllowedException {
		List<Activity> activites = project.getActivites();
		boolean flag = false;
		for(Activity a : activites){
			if (a.getDescription() == activityName){
				flag = true;
			}
		}
		assertFalse(flag);
	}
	
	@Given("{string} claims position project manager")
	public void claimsPositionProjectManager(String Id) {
		try {
			managementSystem.togglePMClaim(project, Id);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}
	
	@When("{string} unclaims position project manager")
	public void unclaimsPositionProjectManager(String Id) {
		claimsPositionProjectManager(Id);
	}
	
	@When("edits expected hour to {double} for activity {string}")
	public void editsExpectedHourToForActivity(double hour, String description) throws OperationNotAllowedException {
		Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
		managementSystem.editExpectedHoursActivity(activity, hour);
	}

	@Then("expected hour for activity {string} is {double}")
	public void expectedHourForActivityIs(String description, double hour) throws OperationNotAllowedException {
       Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
       assertEquals(activity.getExpectedHours(), hour, 0);
	}

	@When("edits activity description to {string} for activity {string}")
	public void editsActivityDescriptionToForActivity(String newDescription, String description) throws OperationNotAllowedException {
		Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
		managementSystem.editProjectActivityDescription(activity, newDescription);
	}

	@Then("description of activity is {string}")
	public void descriptionOfActivityIs(String description) throws OperationNotAllowedException {
		Activity activity = managementSystem.findActivityByDescription(project.getProjectID(), description);
	    assertEquals(activity.getDescription(), description);
	}
}
