package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManagementSystemApp {
	private boolean adminLoggedIn = false;
	private boolean employeeLoggedIn = false;
	private Employee employeeLoggedInId;
	private List<Project> projectRepository = new ArrayList<>();
	private List<Employee> Employees = new ArrayList<>();
	//private DateServer dateServer = new DateServer(); 

	public boolean adminLoggedIn() {
		return adminLoggedIn;
	}
	
	public boolean adminLogin(String id) {
		adminLoggedIn = id.equals("admi");
		return adminLoggedIn;
	}
    
	public boolean adminLogout() {
		adminLoggedIn = false;
		return adminLoggedIn;
	}
	
	public boolean employeeLogged() {
		return employeeLoggedIn;
	}
	
	public boolean employeeLogin(String id) throws OperationNotAllowedException {
		if(!containsEmployeeWithId(id)) {
			throw new OperationNotAllowedException("Employee ID does not exist");
		}
		employeeLoggedIn = true;
		employeeLoggedInId = FindEmployeeById(id);
		return employeeLoggedIn;
	}
    
	public boolean employeeLogout(String id) {
		employeeLoggedInId = null;
		employeeLoggedIn = false;
		return employeeLoggedIn;
	}
	
	// Adds employee to the system
	public void addEmployee(Employee employee) throws OperationNotAllowedException {
		if (!adminLoggedIn) {
			throw new OperationNotAllowedException("Administrator login required");
		}
		
		if(containsEmployeeWithId(employee.getId())){
			throw new OperationNotAllowedException("Employee ID already taken");
		}
		
		Employees.add(employee);
	}
	
	public void removeEmployee(Employee employee) {
		Employees.remove(employee);
	}
	
	public void checkAdminLoggedIn() throws OperationNotAllowedException {
		if (!adminLoggedIn) {
			throw new OperationNotAllowedException("Administrator login required");
		}
	}

	public void checkEmployeeLoggedIn() throws OperationNotAllowedException {
		if (!employeeLoggedIn) {
			throw new OperationNotAllowedException("Employee login required");
		}
	}
	
	public void createProject(Project project) throws OperationNotAllowedException {
		checkAdminLoggedIn();
		projectRepository.add(project);
	}
	
	public Employee FindEmployeeById(String id) throws OperationNotAllowedException{
		Employee employee = Employees.stream().filter(u -> u.getId().equals(id)).findAny().orElse(null);
		
		if(employee == null){
			throw new OperationNotAllowedException("Employee ID does not exist");
		}
		return employee;
	}
	
	public Project findProjectById(int id) throws OperationNotAllowedException{
		Project project = projectRepository.stream().filter(u -> u.getProjectID() == (id)).findAny().orElse(null);
		
		return project;
	}
	
	// adds employee to project
	public void addEmployeeToProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(EmployeeId);
		Project project = findProjectById(ProjectId);
		
		
		if (checkAuth(project)) {
			project.addEmployee(employee);
			return;
		}
	}
	
	public boolean containsEmployeeWithId(String id){
		for(Employee e : Employees){
			if(e.getId().equals(id)) { return true;}
		}
		
		return false;
	}
	
	public boolean checkIfEmployeeIsPartOfProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(EmployeeId);
		Project project = findProjectById(ProjectId);
		
		return project.findEmployee(employee);
		
	}
	
	public boolean checkIfProjectHasPM(int ProjectId) throws OperationNotAllowedException {
		Project project = findProjectById(ProjectId);
		return project.getProjectManager() != null ? true : false;
	}
	
	public void removeEmployeeWithIdFromProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(EmployeeId);
		Project project = findProjectById(ProjectId);
		
		if (checkAuth(project)) {
			project.removeEmployee(employee);
			return;
		}
	}
	
	/*
	public void setDateServer(DateServer dateServer) {
		this.dateServer = dateServer;		
	}
	*/
	public boolean checkIfUniqueProjectId(int Id) {
		int num = 0;
		for(Project i : projectRepository) {
			if(i.getProjectID() == Id) { 
				num++;
			}
		}
		return (num == 1);
	}
	
	public void closeProject(Project project) throws OperationNotAllowedException {
		if (checkAuth(project)) {
			project.closeProject();
		}
	}
	
	public void promoteToPm(int projectId, String Id) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		
		if (checkAuth(project)) {
			project.promoteEmployee(Id);
			return;
		}
	}
	
	public void editProjectName(int projectId, String projectName) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		
		if (checkAuth(project)) {
			project.editProjectName(projectName);
			return;
		}
	}
	
	public void editStartDate(int projectId, int days) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		
		if (checkAuth(project)) {
			Calendar startDate = project.getStartDate();
			startDate.add(Calendar.DAY_OF_YEAR, days);
			project.editStartDate(startDate);
			return;
		}
		 new OperationNotAllowedException("Project Manager login required");
	}
	
	public void editEndDate(int projectId, int days) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		
		if (checkAuth(project)) {
			Calendar endDate = project.getEndDate();
			endDate.add(Calendar.DAY_OF_YEAR, days);
			project.editEndDate(endDate);
			return;
		}
	}
	
	public boolean CheckifStartDateMoved(int projectId, int days, Calendar date) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		Calendar Datee = date;
		Datee.add(Calendar.DAY_OF_YEAR, days);
		return project.getStartDate() == Datee ? true : false;
	}
	
	public boolean CheckifEndDateMoved(int projectId, int days, Calendar date) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		Calendar Datee = date;
		Datee.add(Calendar.DAY_OF_YEAR, days);
		return project.getEndDate() == Datee ? true : false;
	}
	
	public void createActivity(int projectId, String description) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		
		if(checkAuth(project)) {
			project.createActivity(description);
		}
		
		throw new OperationNotAllowedException("Admin or Project Manager log in required");
	}
	
	public Activity findActivityByDescription(int projectId, String description) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		return project.findActivityByDescrption(description);
	}
	
	public void UpdateExpectedHours(int projectId, double hours) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		
		if(checkAuth(project)) {
			project.editExpectedHours(hours);
			}
	}
	
	public void UpdateStartDate(int dd, int mm, int yyyy, int projectId, String description) throws OperationNotAllowedException{
		Activity activity = findActivityByDescription(projectId, description);
		Project project = findProjectById(projectId);
		Calendar startDate = activity.getStartDate();
		startDate = setDate(startDate, dd, mm, yyyy);
		if (checkAuth(project)) {
			activity.setStartDate(startDate);
			return;
		}
	}
	
	public void UpdateEndDate(int dd, int mm, int yyyy, int projectId, String description) throws OperationNotAllowedException{
		Activity activity = findActivityByDescription(projectId, description);
		Project project = findProjectById(projectId);
		Calendar endDate = activity.getEndDate();
		endDate = setDate(endDate, dd, mm, yyyy);
		if (checkAuth(project)) {
			activity.setEndDate(endDate);
			return;
		}
	}
	
	public Calendar setDate(Calendar date, int dd, int mm, int yyyy) {
		date.set(Calendar.YEAR, yyyy);
		date.set(Calendar.MONTH, mm);
		date.set(Calendar.DAY_OF_MONTH, dd);
		return date;
	}

	public void setActivityDescrption(Project project, String description1, String description2) throws OperationNotAllowedException {
		if(checkAuth(project)) {
			project.findActivityByDescrption(description1).setDescrption(description2);
		}
	}
	
	public void generateStatusReport(int projectId) throws OperationNotAllowedException{
		Project project = findProjectById(projectId);
		if (checkAuth(project)) {
			project.generateStatusReport();
			
		}
	}
	
	public void addEmployeeToActivity(Employee employee, Project project, String description) throws OperationNotAllowedException{
		project.addEmployeeToActivity(employee, description);
		employee.addActivity(project, project.findActivityByDescrption(description));
	}
	
	private boolean checkAuth(Project project) throws OperationNotAllowedException {
		if(employeeLoggedIn && project.hasProjectManager()) {
			if(!employeeLoggedInId.equals(project.getProjectManager())) { 
				// throws error if employee logged in is not PM
				throw new OperationNotAllowedException("Project Manager login required");
			}
			return true; // returns true PM is logged in
		} 
		if (project.findEmployee(employeeLoggedInId) && !project.hasProjectManager()){
			return true; // returns true if project has no PM and employee (who is part of project) is logged in
		}
		if(adminLoggedIn()) {
			return true;
		}
		//return false;
		throw new OperationNotAllowedException("Project Manager login required");
	}

//	public List<Activity> requestEmployeeActivity(int projectID, Employee otherEmployee) throws OperationNotAllowedException {
//		Project project = findProjectById(projectID);		
//		if(employeeLoggedIn) {
//			if(employeeLoggedInId.equals(project.getProjectManager())) {
//				return otherEmployee.getActivities();
//			}
//		}
//		throw new OperationNotAllowedException("Project Manager required");
//	}	
}

