package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.Calendar;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ManagementSystemApp extends Observable {
	private boolean adminLoggedIn = false;
	private boolean employeeLoggedIn = false;
	private Employee employeeLoggedInId;
	private List<Project> projectRepository = new ArrayList<>();
	private List<Employee> Employees = new ArrayList<>();

	public boolean adminLoggedIn() {
		return adminLoggedIn;
	}
	
	public boolean adminLogin(String id) {
		adminLoggedIn = id.equals("admi");
		setChanged(); 
		notifyObservers();
		return adminLoggedIn;
	}
    
	public boolean adminLogout() {
		adminLoggedIn = false;
		setChanged();
		notifyObservers();
		return adminLoggedIn;
	}
	
	public boolean employeeLogged() {
		return employeeLoggedIn;
	}
	
	public Employee currentEmployee() {
		return employeeLoggedInId;
	}
	
	public boolean employeeLogin(String id) throws OperationNotAllowedException {
		if(!containsEmployeeWithId(id)) {
			throw new OperationNotAllowedException("Employee ID does not exist");
		}
		employeeLoggedIn = true;
		employeeLoggedInId = FindEmployeeById(id);
		setChanged();
		notifyObservers();
		return employeeLoggedIn;
	}
    
	public boolean employeeLogout() {
		employeeLoggedInId = null;
		employeeLoggedIn = false;
		setChanged();
		notifyObservers();
		return employeeLoggedIn;
	}
	
	public List<Project> getProjectList(){
		return this.projectRepository;
	}
	
	// Adds employee to the system
	public void addEmployee(Employee employee) throws OperationNotAllowedException {
		if (!adminLoggedIn) {
			throw new OperationNotAllowedException("Administrator login required");
		}
		
		if(containsEmployeeWithId(employee.getId())){
			throw new OperationNotAllowedException("Employee ID already taken");
		}
		
		if(employee.getId().length() > 4){
			throw new OperationNotAllowedException("Employee ID is too long");
		}
		
		Employees.add(employee);
	}
	
	// Removes employee from the system
	public void removeEmployee(Employee employee) throws OperationNotAllowedException {
		if(!adminLoggedIn()) {															// 1
			throw new OperationNotAllowedException("Administrator login required");		// 2
		}
		for(Project p : projectRepository) {											// 3
			if(p.getEmployeesAssignedToProject().contains(employee)) {					// 4
				p.removeEmployee(employee);												// 5
			}
		}
		if(Employees.contains(employee)) {												// 6
			Employees.remove(employee);													// 7
		}
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
		if(project == null) {
			throw new OperationNotAllowedException("Project Id does not exist");
		}
		return project;
	}
	
	// adds employee to project
	public void addEmployeeToProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(EmployeeId);
		Project project = findProjectById(ProjectId);
		
		if(employeeLogged() && project.hasProjectManager() && employeeLoggedInId.equals(project.getProjectManager())) {
			project.addEmployee(employee);
			return;
		} 
		if (employeeLogged() && !project.hasProjectManager()){
			project.addEmployee(employee);
			return;
		}
		if(adminLoggedIn()) {
			project.addEmployee(employee);
			return;
		}
		//return false;
		throw new OperationNotAllowedException("Project Manager login required");
		
	}
	
	// removes employee from project
	public void removeEmployeeWithIdFromProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(EmployeeId);
		Project project = findProjectById(ProjectId);
		
		if (checkAuth(project)) {
			project.removeEmployee(employee);
			return;
		}
	}
	
	public boolean containsEmployeeWithId(String id){
		for(Employee e : Employees){
			if(e.getId().equals(id)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkIfEmployeeIsPartOfProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Project project = findProjectById(ProjectId);
		for(Employee i : project.getEmployeesAssignedToProject()) {
			if(i.getId().equals(EmployeeId)) { return true; }
		}
		return false;
	}
	
	public boolean checkIfProjectHasPM(int ProjectId) throws OperationNotAllowedException {
		Project project = findProjectById(ProjectId);
		return project.hasProjectManager();
	}
	
	public boolean checkIfUniqueProjectId(int Id) {
		int num = 0;
		for(Project i : projectRepository) {
			if(i.getProjectID() == Id) { 
				num++;
			}
		}
		return (num == 1);
	} 

	public void promoteToPm(int projectId, String Id) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		Employee employee = FindEmployeeById(Id);
		
		if (checkAuth(project)) {
			project.promoteEmployee(Id);
			return;
		}
	}
	
	public void removePm(int projectId) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		
		if (checkAuth(project)) {
			project.removeProjectManager();
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

	public void createActivity(int projectId, String description) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		
		if(checkAuth(project)) {
			//if(project.getActivites().contains(description))
			project.createActivity(description);
			return;
		}
		
	}
	
	public Activity findActivityByDescription(int projectId, String description) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		return project.findActivityByDescription(description);
	}
	
	public void UpdateExpectedHours(int projectId, double hours) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		
		if(checkAuth(project)) {
			project.editExpectedHours(hours);
		}
	}
	
	// Update start date for activity in project
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
	
	// Update end date for activity in project
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
	
	// Update start date for activity in project
	public void UpdateStartDateProject(int dd, int mm, int yyyy, int projectId) throws OperationNotAllowedException{
		Project project = findProjectById(projectId);
		Calendar startDate = project.getStartDate();
		startDate = setDate(startDate, dd, mm, yyyy);
		if (checkAuth(project)) {
			project.editStartDate(startDate);
			return;
		}
	}
	
	// Update end date for activity in project
	public void UpdateEndDateProject(int dd, int mm, int yyyy, int projectId) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		Calendar endDate = project.getEndDate();
		endDate = setDate(endDate, dd, mm, yyyy);
		if (checkAuth(project)) {
			project.editEndDate(endDate);
			return;
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
	
	// Claim/unclaim projectmanager status
	public boolean togglePMClaim(Project project, String id) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(id);							// 1
		
		if(employeeLoggedInId.equals(project.getProjectManager())) {		// 2
			removePm(project.getProjectID());								// 3
			return false;													// 4
		} 
		
		if (employeeLogged() && !project.hasProjectManager()){				// 5
			promoteToPm(project.getProjectID(), employee.getId());			// 6
			return true;													// 7
		}
		
		throw new OperationNotAllowedException("Project already has PM");	// 8
	}
	
	public Calendar setDate(Calendar date, int dd, int mm, int yyyy) {
		date.set(Calendar.YEAR, yyyy);
		date.set(Calendar.MONTH, mm);
		date.set(Calendar.DAY_OF_MONTH, dd);
		return date;
	}

	public void setActivityDescrption(Project project, String description1, String description2) throws OperationNotAllowedException {
		if(checkAuth(project)) {
			project.findActivityByDescription(description1).setDescrption(description2);
		}
	}
	
	// Add employee to activity in project
	public void addEmployeeToActivity(Employee employee, Project project, String description) throws OperationNotAllowedException{
		if(checkAuth(project)) {
			if(project.findEmployee(employee)) {
				// Adds employee to project employee list
				project.addEmployeeToActivity(employee, description);
				
				// Adds activity to employee project-activity list
				employee.addActivity(project, project.findActivityByDescription(description));
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
	
	public List<Activity> getActivities(int projectId, Employee anotherEmployee) throws OperationNotAllowedException{
		Project project = findProjectById(projectId);
		
		if(checkAuth(project)){
			return anotherEmployee.listOfActivitiesInProject(project);
		}
		return null;
		//throw new OperationNotAllowedException("addEmployeeToActivity error");
	}
	
	private boolean checkAuth(Project project) throws OperationNotAllowedException {
		if(employeeLoggedIn && project.hasProjectManager()) {															
			if(!employeeLoggedInId.equals(project.getProjectManager())) { 												
				// throws error if employee logged in is not PM
				throw new OperationNotAllowedException("Project Manager login required");							
			}
			return true; // returns true PM is logged in																
		} 
		if (employeeLoggedIn && project.findEmployee(currentEmployee()) && !project.hasProjectManager()){				
			return true; // returns true if project has no PM and employee (who is part of project) is logged in		
		}
		if(adminLoggedIn()) {																							
			return true;																								
		}
		//return false;
		throw new OperationNotAllowedException("Project Manager login required");								
	}
	
	// Removes activity from project and employees
	public void removeActivity(Activity activity) throws OperationNotAllowedException {
		Project project = findProjectById(activity.getProjectId());
		List<Employee> e = activity.getEmployees();
		
		if(checkAuth(project)) {	
			for(Employee em : e) {
				em.removeActivity(project, activity);
			}
			project.removeActivity(activity);
		}
	}
	
	// Remove project from repository
	public void removeProject(Project project) throws OperationNotAllowedException {
		List<Employee> empl = project.getEmployeesAssignedToProject();
		
		if(adminLoggedIn() || currentEmployee().equals(project.getProjectManager())) {
			for(Employee e : empl){
				for(Activity a : e.getActivities())
					e.removeActivity(project, a);
			}
			
			projectRepository.remove(project);
			return;
		}
		throw new OperationNotAllowedException("Requirement not met");
		
	}
	
	public List<Project> searchProject(String searchText) {
		return projectRepository.stream()
				.filter(b -> b.match(searchText))
				.collect(Collectors.toList());
	}
	
	public List<Activity> searchActivity(String searchText) {
		List<Activity> activites =  new ArrayList<>();
	
			for(Project p : projectRepository) {
				activites.addAll(p.getActivites().stream()
				.filter(b -> b.match(searchText))
				.collect(Collectors.toList()));
			}
		
		setChanged(); 
		notifyObservers();
		return activites;
	}
	
	public List<Employee> searchEmployee(String searchText) {
		return Employees.stream()
				.filter(b -> b.match(searchText))
				.collect(Collectors.toList());
	}
	
	// Add worked hour to activity
	public void addHourToActivity(Activity activity, double hours) throws OperationNotAllowedException {
		Project project = findProjectById(activity.getProjectId());
	
		checkEmployeeLoggedIn();
		
		currentEmployee().addProjectActivity(project, activity);
		project.addHoursToActivity(activity, currentEmployee(), hours);		
	}
	
	public void editExpectedHoursActivity(Activity activity, Double hours) throws OperationNotAllowedException {
		Project project = findProjectById(activity.getProjectId());
		if(checkAuth(project)){
			activity.setExpectedHours(hours);
		}
	}
	
	public void editProjectActivityDescription(Activity activity, String description) throws OperationNotAllowedException{
		Project project = findProjectById(activity.getProjectId());
		if(checkAuth(project)){
			activity.setDescrption(description);
		}
	}
	
	public String projectDetails(Project project) throws OperationNotAllowedException {
			return project.printDetail();
	}
	
	public String activityDetails(Activity activity) throws OperationNotAllowedException {
		return activity.printDetail();
}
	
	public String getStatusOfEmployee(Employee employee, boolean active) {
		return employee.getStatusOfEmployee(active);
	}
	
	public String getStatReportOfProject(Project project) throws OperationNotAllowedException {
		if(checkAuth(project)){
			return project.getStatusReport();
		}
		return null;
	}
	
	public void editActivityTimeTable(Activity activity, Calendar date, double workHours) throws OperationNotAllowedException {
		Project project = findProjectById(activity.getProjectId());
		project.editTimeTable(activity, currentEmployee(), date, workHours);
	}
	
	public List<TimeTable> getActivityTimeTableCurrentEmployee(Activity activity) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		Project project = findProjectById(activity.getProjectId());
		List<TimeTable> employeeActivityTimeTable = project.getTimeTableForEmployeeAndActivity(currentEmployee(), activity, project.getTimeTables());
		
		return employeeActivityTimeTable;
	}
	
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

