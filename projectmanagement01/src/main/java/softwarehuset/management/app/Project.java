package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class Project {
	private String projectName;
	private double expectedHours;
	private Calendar startDate, endDate;
	
	private Boolean ongoingProject = false;
	
	private List<Employee> employeesAssignedToProject = new ArrayList<>();
	private List<Activity> activities = new ArrayList<>();
	private List<TimeTable> timeTables = new ArrayList<>();
	private IDServer idServer = new IDServer();
	private Employee projectManager;
	
	private int projectID;
	
	public Project(String projectName, Double expectedHours, Calendar startDate, Calendar endDate) {
		this.projectName = projectName;
		this.expectedHours = 0 > expectedHours ? 0 : expectedHours - expectedHours % 0.5;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectID = idServer.generateID(startDate);
	} 
	
	// Get project name
	public String getProjectName() {
		return projectName;
	}
	
	// Check if project manager assigned
	public boolean hasProjectManager() {
		return projectManager != null ? true : false;
	}
	
	// Get project manager
	public Employee getProjectManager() {
		return projectManager;
	}
	
	// Get project id
	public int getProjectID() {
		return projectID;
	}
	
	// Get start date
	public Calendar getStartDate() {
		return startDate;
	}
	
	// Get end date
	public Calendar getEndDate() {
		return endDate;
	}
	
	// See if the project is active or closed
	public boolean getOngoingProject() {
		return ongoingProject;
	}
	
	// Get employees working on this project
	public List<Employee> getEmployeesAssignedToProject(){
		return employeesAssignedToProject;
	}
	
	// Get activities in this project
	public List<Activity> getActivites(){
		return activities;
	}
	
	// Get expected hour of project
	public double getExpectedHours() {
		return expectedHours;
	}
	
	// Get worked hours on this project
	public double getWorkedHours(){
		double sumWorkedHours = 0;
		for(Activity a : activities){
			sumWorkedHours += a.getWorkedHours();
		}
		return sumWorkedHours;
	}
			
	
	// Get time tables associated with this project
	public List<TimeTable> getTimeTables(){
		return this.timeTables;
	}
	
	// Get time tables of all employees working on this project
	public List<Employee> getEmployeesFromTimeTable(Activity a){
		List<Employee> es = new ArrayList<>(a.getEmployees());
		for(TimeTable t : this.timeTables) {
			if(!es.contains(t.getEmployee()) /*&& (t.getHoursWorked() != 0)*/ && (t.getActivity().equals(a))) {
				es.add(t.getEmployee());
			}
		}
		return es;
	}

	// Get time tables associated with an employee working on this project
	public List<TimeTable> getTimeTablesByEmployee(Employee employee) {
		List<TimeTable> employeeTimeTables = timeTables.stream().filter(u -> u.getEmployee().equals(employee)).collect(Collectors.toList());
		return employeeTimeTables;
	}
		
	// Get time tables at specific date associated with an employee working on this project
	public TimeTable getTimeTablesByDateAndEmployee(Employee employee, Calendar date) {
		List<TimeTable> employeeTimeTables = timeTables.stream().filter(u -> u.getEmployee().equals(employee)).collect(Collectors.toList());
		TimeTable finalTimeTable = employeeTimeTables.stream().filter(u -> u.getDate().equals(date)).findAny().orElse(null);
		return finalTimeTable;
	}
	
	// Get time tables for a specific employees and a specific activity
	public List<TimeTable> getTimeTableForEmployeeAndActivity(Employee employee, Activity activity, List<TimeTable> timeTableInput){
		List<TimeTable> list = new ArrayList<>();
		list = getTimeTableForEmployee(employee, timeTableInput);
		list = getTimeTableForActivity(activity, list);
		return list;
	}
	
	// Edit project name
	public void editProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	// Edit expected hours on project
	public void editExpectedHours(Double expectedHours) {
		this.expectedHours = 0 > expectedHours ? 0 : expectedHours - expectedHours % 0.5 ;
	}
	
	// Edit start date of project
	public void editStartDate(Calendar newStartDate) {
		this.startDate = newStartDate;
	}
	
	// Edit end date of project
	public void editEndDate(Calendar newEndDate) {
		this.endDate = newEndDate;
	}
	
	// Set project as active (true)
	public void beginProject(){
		ongoingProject = true;
	}
	
	// set project as inactive (false)
	public void closeProject() {
		ongoingProject = false;
	}
	
	// Override and assign the project manager for this project, even if already assigned
	public void setProjectManager(Employee employee) { 
		this.projectManager = employee;
	}
	
	// Remove current project manager
	public void removeProjectManager(){
		this.projectManager = null;
	}
	
	// Promote an employee part of this project to project manager
	// Throws Exception if project manager is already assigned or employee is not part of project
	public void promoteEmployee(String id) throws OperationNotAllowedException{
		if(projectManager != null) {
			throw new OperationNotAllowedException("Project already has Project Manager");
		}
		
		for(Employee e : employeesAssignedToProject){
			if(e.getId().equals(id) == true){
				setProjectManager(e);
				return;
			}
		}
		
		throw new OperationNotAllowedException("Employee is not part of the project");
	}
	
	// Add employee - Throws Exception if employee is already part of project
	public void addEmployee(Employee employee) throws OperationNotAllowedException { 
		if(!employeesAssignedToProject.contains(employee)){
			employeesAssignedToProject.add(employee);
		} else {
			throw new OperationNotAllowedException("Employee already part of project");
		}
	}
	
	// Remove employee - Throws Exception if employee is not part of project
	public void removeEmployee(Employee employee) throws OperationNotAllowedException {
		if(employeesAssignedToProject.contains(employee)){
			employeesAssignedToProject.remove(employee);
			if(projectManager == employee) {
				removeProjectManager();
			}
			
			for(Activity a : activities){
				if(a.getEmployees().contains(employee)){
					a.removeEmployee(employee);
				}
			}
			//return;
		} else {
			throw new OperationNotAllowedException("Employee is not part of project");
		}
		
	}
	
	// Create an activity for project 
	// Throws exception if activity is unnamed or if another activity in this project is named the same
	public void createActivity(String description) throws OperationNotAllowedException {
		if(description == "") {
			throw new OperationNotAllowedException("Activities must have a name");
		}
		for(Activity a : activities){
			if(a.getDescription().equals(description)) {
				throw new OperationNotAllowedException("Activities must have a unique name");
			}
		}
		
		Activity activity = new Activity(projectID, description, startDate, endDate);
		addActivity(activity);
	}
	
	// Add activity - Throws Exception if activity is already part of project
	public void addActivity(Activity activity) throws OperationNotAllowedException { 
		if(!activities.contains(activity)){
			activities.add(activity);
		} else {
			throw new OperationNotAllowedException("Activity already part of project");
		}
	}
	
	// Remove activity - Throws Exception if activity is not part of project
	public void removeActivity(Activity activity) throws OperationNotAllowedException {
		if(!activities.contains(activity)) {
			throw new OperationNotAllowedException("activity is not part of project");
		}
		activities.remove(activity);
	}
	
	public boolean findEmployee(Employee employee) throws OperationNotAllowedException {
		for(Employee e : employeesAssignedToProject){
			if(e.equals(employee) == true){
				return true;
			}
		}
		return false;
	}
	
	// Find a specific activity associated with this project by it's description
	public Activity findActivityByDescription(String description) throws OperationNotAllowedException {
		for(Activity activity : activities) {
			if(activity.getDescription().equals(description)) {
				return activity;
			}
		}
		throw new OperationNotAllowedException("Activity does not exist");
	}
	
	// Add an employee to specific activity associated wit this project
	public void addEmployeeToActivity(Employee employee, String description) throws OperationNotAllowedException{
		Activity a = findActivityByDescription(description);
		if(findEmployee(employee)){
			a.addEmployee(employee);
		}
	}
	
	// Remove an employee from specific activity associated with this project
	public void removeEmployeeFromActivity(Employee employee, String description) throws OperationNotAllowedException{
		Activity a = findActivityByDescription(description);
		if(findEmployee(employee)){
			a.removeEmployee(employee);
		}
	}
	
	// Edit a specific time table that an employee has made in an activity with this project
	public void editTimeTable(Activity activity, Employee employee, Calendar date, double workHours) {
		TimeTable timeTable = getTimeTablesByDateAndEmployee(employee, date);
		timeTable.editActivity(activity);
		timeTable.editEmployee(employee);
		timeTable.editDate(date);
		timeTable.editHours(workHours);
	}
	
	// Add worked hours to an activity
	public void addHoursToActivity(Activity activity, Employee employee, double hours){
		activity.addWorkedHours(hours);
		TimeTable timeTable = new TimeTable(activity, employee, hours);
		timeTables.add(timeTable);
	}
	
	//When searching with a key word these projects appear:
	public boolean match(String searchText) {
		if(this.hasProjectManager()) {
			return projectName.contains(searchText) || projectManager.getName().contains(searchText) || projectManager.getId().contains(searchText.toLowerCase());
		} else {
			return projectName.contains(searchText);
		}
	}
	
	//To display on GUI what comes up when we search for a key word
	public String toString() {
		String name = this.getProjectName().isBlank() ? "No project name yet" : this.getProjectName();
		return "Project ID: " + String.valueOf(getProjectID()) + " - " + name;
	}
	
	//Private method to get time tables for a certain employee
	private List<TimeTable> getTimeTableForEmployee(Employee employee, List<TimeTable> timeTableInput){
		List<TimeTable> timeTablesWithEmployee = new ArrayList<>();
		
		for(TimeTable t : timeTableInput) {
			if(t.getEmployee().equals(employee)) {
				timeTablesWithEmployee.add(t);
			}
		}
		return timeTablesWithEmployee;
	}
	
	//Private method to get time tables for a certain activity
	private List<TimeTable> getTimeTableForActivity(Activity activity, List<TimeTable> timeTableInput){
		List<TimeTable> timeTablesWithEmployee = new ArrayList<>();
		
		for(TimeTable t : timeTableInput) {
			if(t.getActivity().equals(activity)) {
				timeTablesWithEmployee.add(t);
			}
		}
		return timeTablesWithEmployee;
	}
}
