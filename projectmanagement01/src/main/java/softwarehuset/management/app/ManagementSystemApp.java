package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.Calendar;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ManagementSystemApp {
//	private ProjectRepository projectRepository = new ProjectRepository();
//	private EmployeeRepository employeeRepository = new EmployeeRepository();
//	private LoginSystem loginSystem = new LoginSystem();
		
	private ProjectRepository projectRepository;
	private EmployeeRepository employeeRepository;
	private LoginSystem loginSystem;
	
	public ManagementSystemApp() {
		this.projectRepository = new ProjectRepository();
		this.employeeRepository = new EmployeeRepository();
		this.loginSystem = new LoginSystem();
		loginSystem.setEmployeeRepository(employeeRepository);		
	}
	
	public LoginSystem getLoginSystem(){
		return loginSystem;
	}
	
	public ProjectRepository getProjectRepository(){
		return projectRepository;
	}
	
	public EmployeeRepository getEmployeeRepository() {
		return employeeRepository;
	}
	
	public PrintDetails getPrintDetails(){
		PrintDetails printDetails = new PrintDetails();
		return printDetails;
	}
	
	public void removeEmployee(Employee employee) throws OperationNotAllowedException{
		if(!getLoginSystem().adminLoggedIn()){											// 1
			throw new OperationNotAllowedException("Administrator login required");		// 2
		}
		for(Project p : getProjectRepository().getProjectRepository()) {				// 3
			if(p.getEmployeesAssignedToProject().contains(employee)) {					// 4
				p.removeEmployee(employee);												// 5
			}
		}
		getEmployeeRepository().removeEmployee(employee);								// 6
	}
	
	// Add project to project repository
	public void createProject(Project project) throws OperationNotAllowedException {
		if(!loginSystem.adminLoggedIn()) {
			throw new OperationNotAllowedException("Admin login required");
		}
		projectRepository.addProject(project);
	}
	
	// Remove project from project repository
	public void removeProject(Project project) throws OperationNotAllowedException {
		
		boolean flagA = loginSystem.adminLoggedIn();
		boolean flagB = employeeIsProjectManager(project, loginSystem.getCurrentLoggedID());
		
		if(!flagA && !flagB) {
			throw new OperationNotAllowedException("Admin or project manager login required");
		}
	
		project.clean(); //Cleans activities from project
		projectRepository.removeProject(project);
	}
	
	
	private boolean employeeIsProjectManager(Project project, String employeeID) throws OperationNotAllowedException {
		Employee employee = employeeRepository.findEmployeeByID(employeeID);
		return employee.equals(project.getProjectManager());
	}	
	
	// Add employee to project
	public void addEmployeeToProject(int projectId, String employeeId) throws OperationNotAllowedException {
		assert (employeeId != null) && ((String.valueOf(projectId).length() <= 5) == true) && (projectId > 0 == true) : "Precondition";
		Employee employee = employeeRepository.findEmployeeByID(employeeId);
		Project project = projectRepository.findProjectByID(projectId);
		
		if(loginSystem.employeeLoggedIn() && project.hasProjectManager() && loginSystem.getCurrentLoggedID().equals(project.getProjectManager().getId())) {
			project.addEmployee(employee);
			assert project.getEmployeesAssignedToProject().contains(employee) == true : "Postcondition";
			return;
		} 
		if (loginSystem.employeeLoggedIn() && !project.hasProjectManager()){
			project.addEmployee(employee);
			assert project.getEmployeesAssignedToProject().contains(employee) == true : "Postcondition";
			return;
		}
		if(loginSystem.adminLoggedIn()) {
			project.addEmployee(employee);
			assert project.getEmployeesAssignedToProject().contains(employee) == true : "Postcondition";
			return;
		}
		//return false;
		throw new OperationNotAllowedException("Project Manager login required");
	}
	
	// Remove employee from project
	public void removeEmployeeWithIdFromProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = employeeRepository.findEmployeeByID(EmployeeId);
		Project project = projectRepository.findProjectByID(ProjectId);
			
		if (checkAuth(project)) {
			project.removeEmployee(employee);
		}
	}
		
	// Promote an employee that is part of a project to project manager
	public void promoteToPm(int projectID, String employeeID) throws OperationNotAllowedException {
		Project project = projectRepository.findProjectByID(projectID);
		
		if (checkAuth(project)) {
			project.promoteEmployee(employeeID);
		}
	}
	
	// Demote a project manager down to employee of a project
	public void removePm(int projectId) throws OperationNotAllowedException {
		Project project = projectRepository.findProjectByID(projectId);
		
		if (checkAuth(project)) {
			project.removeProjectManager();
		}
	}
	
	// Find activity by description1 and edit to activity description2
	public void setActivityDescrption(Project project, String description1, String description2) throws OperationNotAllowedException {
		if(checkAuth(project)) {
			project.findActivityByDescription(description1).setDescrption(description2);
		}
	}

	// Edit an activities description
	public void editProjectActivityDescription(Activity activity, String description) throws OperationNotAllowedException{
		Project project = projectRepository.findProjectByID(activity.getProjectId());
		if(checkAuth(project)){
			activity.setDescrption(description);
		}
	}
	
	// Add worked hour to activity
	public void addHourToActivity(Activity activity, double hours) throws OperationNotAllowedException {
		loginSystem.checkEmployeeLoggedIn();
		
		Project project = projectRepository.findProjectByID(activity.getProjectId());
		Employee currentEmployee = employeeRepository.findEmployeeByID(loginSystem.getCurrentLoggedID());
		
		currentEmployee.addProjectActivity(project, activity);
		project.addHoursToActivity(activity, currentEmployee, hours);		
	}
	
	// Edit expected hours of an activity
	public void editExpectedHoursActivity(Activity activity, Double hours) throws OperationNotAllowedException {
		Project project = projectRepository.findProjectByID(activity.getProjectId());
		if(checkAuth(project)){
			activity.setExpectedHours(hours);
		}
	}
	
	/*
	// Edit expected hours of an ectivity
	public void updateExpectedHours(int projectId, double hours) throws OperationNotAllowedException {
		Project project = projectRepository.findProjectByID(projectId);
		
		if(checkAuth(project)) {
			project.editExpectedHours(hours);
		}
	}
	*/
	
	// Add employee to activity in project
	public void addEmployeeToActivity(Employee employee, Project project, String description) throws OperationNotAllowedException{
		if(checkAuth(project)) {
			if(project.findEmployee(employee)) {
				// Adds employee to project employee list
				project.addEmployeeToActivity(employee, description);
				Activity a = project.findActivityByDescription(description);	
				// Adds activity to employee project-activity list
				employee.addActivity(project, a);
				return;
			}
			throw new OperationNotAllowedException("Employee not part of project");
		}
	}
	
	// Remove employee from activity in project
	public void removeEmployeeFromActivity(Employee employee, Project project, String description) throws OperationNotAllowedException{
		if(checkAuth(project)) {
			if(project.findEmployee(employee)) {
				// Removes employee to project employee list
				project.removeEmployeeFromActivity(employee, description);
				
				// Remove activity from employee project-activity list
				employee.removeActivity(project, project.findActivityByDescription(description));
				return;
			}
			throw new OperationNotAllowedException("Employee not part of project");
		}
	}
	
	// Toggle the project status - ON/OFF
	public void toggleProjectOngoing(Project project) throws OperationNotAllowedException {		
		if(checkAuth(project)){							
			if (project.getOngoingProject()){			
				project.closeProject();					
			} else{										
				project.beginProject();					
			}
		}
	}
	
	// Claim/unclaim project manager status
	public boolean togglePMClaim(Project project, String id) throws OperationNotAllowedException {
		//Project project = projectRepository.findProjectByID(projectId);
		assert (loginSystem.employeeLoggedIn() == true) && (project != null) && (id != null) && (id.length() <= 4) && (id.length() > 0): "Precondition";
		Employee employee = employeeRepository.findEmployeeByID(id);							
		
		if(project.hasProjectManager() && loginSystem.getCurrentLoggedID().equals(project.getProjectManager().getId())) {	
			project.removeProjectManager();
			assert project.getProjectManager() != employee : "Postcondition";
			return false;			
		} 
		
		if (loginSystem.employeeLoggedIn() && !project.hasProjectManager()){
			project.promoteEmployee(id);	
			Employee e = project.getProjectManager();
			assert e.getId().equals(id) : "Postcondition";
			
			return true;													
		}
		
		assert project.hasProjectManager() == true : "Postcondition";
		throw new OperationNotAllowedException("Project already has PM");	
	}
	
	
	
	// Create a specific date as dd-mm-yyyy
	public Calendar setDate(Calendar date, int dd, int mm, int yyyy) {
		date.set(Calendar.YEAR, yyyy);
		date.set(Calendar.MONTH, mm);
		date.set(Calendar.DAY_OF_MONTH, dd);
		return date;
	}
	
	// Get list of activities from an employee
	public List<Activity> getActivities(int projectId, Employee anotherEmployee) throws OperationNotAllowedException{
		Project project = projectRepository.findProjectByID(projectId);
		
		if(checkAuth(project)){
			return anotherEmployee.listOfActivitiesInProject(project);
		}
		return null;
	}
	
	// Edit a specific time table
	public void editActivityTimeTable(Activity activity, Calendar date, double workHours) throws OperationNotAllowedException {
		Project project = projectRepository.findProjectByID(activity.getProjectId());
		Employee employee = employeeRepository.findEmployeeByID(loginSystem.getCurrentLoggedID());
		project.editTimeTable(activity, employee, date, workHours);
	}
	
	// Get list of time tables of the current employee logged in
	public List<TimeTable> getActivityTimeTableCurrentEmployee(Activity activity) throws OperationNotAllowedException {
		Employee currentEmployee = employeeRepository.findEmployeeByID(loginSystem.getCurrentLoggedID());
		
		loginSystem.checkEmployeeLoggedIn();
		Project project = projectRepository.findProjectByID(activity.getProjectId());
		List<TimeTable> employeeActivityTimeTable = project.getTimeTableForEmployeeAndActivity(currentEmployee, activity, project.getTimeTables());
		
		return employeeActivityTimeTable;
	}
		
	// Search for all activities in system by key word
	public List<Activity> searchActivity(String searchText) {
		List<Activity> activites =  new ArrayList<>();
	
		for(Project p : projectRepository.getProjectRepository()) {			
			activites.addAll(p.getActivites().stream()
			.filter(b -> b.match(searchText))
			.collect(Collectors.toList()));
		}
		return activites;
	}
	
	// Check authentication of the current employee logged in
	public boolean checkAuth(Project project) throws OperationNotAllowedException {
		assert (project != null): "Precondition";
		Employee currentEmployee = null;
		
		if(loginSystem.employeeLoggedIn()) {
			currentEmployee = employeeRepository.findEmployeeByID(loginSystem.getCurrentLoggedID());
		}
		
		if(loginSystem.employeeLoggedIn() && project.hasProjectManager()) {															
			if(!currentEmployee.equals(project.getProjectManager())) { 												
				// throws error if employee logged in is not PM
				throw new OperationNotAllowedException("Project Manager login required");							
			}
			return true; // returns true PM is logged in																
		} 
		if (loginSystem.employeeLoggedIn() && project.findEmployee(currentEmployee) && !project.hasProjectManager()){				
			return true; // returns true if project has no PM and employee (who is part of project) is logged in		
		}
		if(loginSystem.adminLoggedIn()) {
			return true;																								
		}		
		throw new OperationNotAllowedException("Project Manager login required");								
	}
	
	// Example data to test the program with
	public void exampleData() throws OperationNotAllowedException {
	/*	
		adminLogin("admi");
		
		Employee employee1 = new Employee("Malthe", "mkr");
		Employee employee2 = new Employee("Niklas", "nik");
		Employee employee3 = new Employee("Hans", "hans");
		Employee employee4 = new Employee("Matthias", "mat");
		Employee employee5 = new Employee("Kjølbro", "bro");
		Employee employee6 = new Employee("Ewald", "ewd");
		Employee employee7 = new Employee("Thor", "thr"); 
		Employee employee8 = new Employee("Rumle", "ruml");
		Employee employee9 = new Employee("professor", "dtu");
		
		
		addEmployee(employee1);
		addEmployee(employee2);
		addEmployee(employee3);
		addEmployee(employee4);
		addEmployee(employee5);
		addEmployee(employee6);
		addEmployee(employee7);
		
		Calendar calendar = new GregorianCalendar();
		Calendar startDate = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		Calendar endDate1 = new GregorianCalendar();
		Calendar endDate2 = new GregorianCalendar();
		Calendar endDate3 = new GregorianCalendar();
		endDate1.setTime(startDate.getTime());
		endDate1.add(Calendar.DAY_OF_YEAR, 2);
		endDate2.setTime(startDate.getTime());
		endDate2.add(Calendar.DAY_OF_YEAR, 7);		
		endDate3.setTime(startDate.getTime());
		endDate3.add(Calendar.DAY_OF_YEAR, 13);		
		
		Project project1 = new Project("GUI", 40.6, startDate, endDate1);
		Project project2 = new Project("ManagementSystemApp", 200.9, startDate, endDate2);
		Project project3 = new Project("Venner i Software Eng. 1", 1000.231, startDate, endDate2);
		Project project4 = new Project("Home Improvement", 20.3, startDate, endDate3);
		Project project5 = new Project("Activity", 30.6, startDate, endDate3);
		
		createProject(project1);
		createProject(project2);
		createProject(project3); 
		createProject(project4);
		createProject(project5);
		
		project1.addEmployee(employee1);
		project1.addEmployee(employee2);
		project1.setProjectManager(employee2);
		
		project2.addEmployee(employee1);
		project2.addEmployee(employee2);
		project2.addEmployee(employee3);
		project2.addEmployee(employee7);
		project2.addEmployee(employee8);
		project2.addEmployee(employee9);
		project2.setProjectManager(employee2);
		
		project3.addEmployee(employee1);
		project3.addEmployee(employee2);
		project3.addEmployee(employee4);
		project3.addEmployee(employee7);
		project3.addEmployee(employee8);
		
		
		project4.addEmployee(employee2);
		project4.addEmployee(employee4);
		project4.addEmployee(employee5);
		project4.addEmployee(employee6);
		project4.setProjectManager(employee6);
		
		project5.addEmployee(employee1);
		project5.addEmployee(employee8);
		project5.setProjectManager(employee8);
		
		
		project1.createActivity("Main Screen");
		project1.createActivity("Find Project");
		project1.createActivity("Find Activity");
		project1.createActivity("Create Project");
		project1.createActivity("Edit Project");
		project1.createActivity("Create Activity");
		project1.createActivity("Edit Activity");
		
		addEmployeeToActivity(employee1, project1, "Main Screen");
		addEmployeeToActivity(employee2, project1, "Main Screen");
		addEmployeeToActivity(employee1, project1, "Find Project");
		addEmployeeToActivity(employee2, project1, "Find Project");
		addEmployeeToActivity(employee1, project1, "Find Activity");
		addEmployeeToActivity(employee2, project1, "Find Activity");
		addEmployeeToActivity(employee1, project1, "Edit Project");
		addEmployeeToActivity(employee2, project1, "Edit Project");
		addEmployeeToActivity(employee1, project1, "Create Activity");
		addEmployeeToActivity(employee2, project1, "Create Activity");
		addEmployeeToActivity(employee1, project1, "Edit Activity");
		addEmployeeToActivity(employee2, project1, "Edit Activity");
		
		project2.createActivity("ManagementSystemApp");
		project2.createActivity("Project");
		project2.createActivity("Activity");
		project2.createActivity("Features");
		project2.createActivity("TimeTable");
		project2.createActivity("OperationNotAllowedException");
		project2.createActivity("Employee");
		
		addEmployeeToActivity(employee1, project2, "ManagementSystemApp");
		addEmployeeToActivity(employee2, project2, "ManagementSystemApp");
		addEmployeeToActivity(employee3, project2, "ManagementSystemApp");
		addEmployeeToActivity(employee7, project2, "ManagementSystemApp");
		addEmployeeToActivity(employee2, project2, "Project");
		addEmployeeToActivity(employee3, project2, "Project");
		addEmployeeToActivity(employee1, project2, "Activity");
		addEmployeeToActivity(employee2, project2, "Activity");
		addEmployeeToActivity(employee3, project2, "Activity");
		addEmployeeToActivity(employee1, project2, "Features");
		addEmployeeToActivity(employee2, project2, "Features");
		addEmployeeToActivity(employee7, project2, "TimeTable");
		addEmployeeToActivity(employee2, project2, "OperationNotAllowedException");
		addEmployeeToActivity(employee3, project2, "OperationNotAllowedException");
		addEmployeeToActivity(employee1, project2, "Employee");
		addEmployeeToActivity(employee7, project2, "Employee");
		
		project3.createActivity("Øl");
		project3.createActivity("Hygge");
		
		project4.createActivity("gulv"); 
		project4.createActivity("flytte");
		
		project5.createActivity("i brug");
		project5.createActivity("kommer snart");
		
		addEmployeeToActivity(employee1, project3, "Øl");
		addEmployeeToActivity(employee2, project3, "Øl");
		addEmployeeToActivity(employee4, project3, "Øl");
		addEmployeeToActivity(employee8, project3, "Øl");
		addEmployeeToActivity(employee1, project3, "Hygge");
		addEmployeeToActivity(employee2, project3, "Hygge");
		addEmployeeToActivity(employee4, project3, "Hygge");
		addEmployeeToActivity(employee7, project3, "Hygge");
		addEmployeeToActivity(employee8, project3, "Hygge");
		
		addEmployeeToActivity(employee4, project4, "gulv");
		addEmployeeToActivity(employee5, project4, "gulv");
		addEmployeeToActivity(employee6, project4, "gulv");
		
		addEmployeeToActivity(employee2, project4, "flytte");
		addEmployeeToActivity(employee4, project4, "flytte");
		addEmployeeToActivity(employee5, project4, "flytte");
		
		
		addEmployeeToActivity(employee1, project5, "i brug");
		addEmployeeToActivity(employee8, project5, "i brug");
		addEmployeeToActivity(employee1, project5, "kommer snart");
		
		adminLogout();
	*/	
	}
}

