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
		this.expectedHours = expectedHours;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectID = idServer.generateID(startDate);
	}
	
	public Project(Double expectedHours, Calendar startDate, Calendar endDate) {
		this.expectedHours = expectedHours;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectID = idServer.generateID(startDate);
	}
	
	// Override and assign the project manager for this project, even if already assigned
	public void setProjectManager(Employee employee) { 
		this.projectManager = employee;
	}
	
	// Add employee - Throws Exception if employee is already part of project
	public void addEmployee(Employee employee) throws OperationNotAllowedException { 
		if(!employeesAssignedToProject.contains(employee)){
			employeesAssignedToProject.add(employee);
		} else {
			throw new OperationNotAllowedException("Employee already part of project");
		}
	}
	
	public void createActivity(String description) throws OperationNotAllowedException {
		if(description == "") {
			throw new OperationNotAllowedException("Activities must have a name");
		}
		for(Activity a : activities){
			if(a.getDescription() == description) {
				throw new OperationNotAllowedException("Activities must have a unique name");
			}
		}
		
		Activity activity = new Activity(projectID, description, startDate, endDate);
		activities.add(activity);
	}
	
	// Add activity - Throws Exception if activity is already part of project
	public void addActivity(Activity activity) throws OperationNotAllowedException { 
		if(!activities.contains(activity)){
			activities.add(activity);
		} else {
			throw new OperationNotAllowedException("Activity already part of project");
		}
	}
	
	public void removeEmployee(Employee employee) throws OperationNotAllowedException {
		if(employeesAssignedToProject.contains(employee)){
			employeesAssignedToProject.remove(employee);
			return;
		}
		throw new OperationNotAllowedException("Employee is not part of project");
	}
	
	public void editProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public void editExpectedHours(Double expectedHours) {
		this.expectedHours = expectedHours;
	}
	
	public void editStartDate(Calendar newStartDate) {
		this.startDate = newStartDate;
	}
	
	public void editEndDate(Calendar newEndDate) {
		this.endDate = newEndDate;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public Employee getProjectManager() {
		return projectManager;
	}
	
	public boolean hasProjectManager() {
		return projectManager != null ? true : false;
	}
	
	public int getProjectID() {
		return projectID;
	}
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public boolean getOngoingProject() {
		return ongoingProject;
	}
	
	public List<Activity> getActivites(){
		return activities;
	}
	
	public double getWorkedHours(){
		double sumWorkedHours = 0;
		for(Activity a : activities){
			sumWorkedHours += a.getWorkedHours();
		}
		return sumWorkedHours;
	}
	
	public void closeProject() {
		ongoingProject = false;
	}
	
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
	
	public boolean findEmployee(Employee employee) throws OperationNotAllowedException {
		for(Employee e : employeesAssignedToProject){
			if(e.equals(employee) == true){
				return true;
			}
		}
		return false;
	}
	
	public Activity findActivityByDescription(String description) throws OperationNotAllowedException {
		for(Activity activity : activities) {
			if(activity.getDescription().equals(description)) {
				return activity;
			}
		}
		throw new OperationNotAllowedException("Activity does not exist");
	}
	
	public void addEmployeeToActivity(Employee employee, String description) throws OperationNotAllowedException{
		Activity a = findActivityByDescription(description);
		if(findEmployee(employee)){
			a.addEmployee(employee);
		}
	}
	
	public double getExpectedHours() {
		return expectedHours;
	}
	
	public List<TimeTable> getTimeTablesByEmployee(Employee employee) {
		List<TimeTable> employeeTimeTables = timeTables.stream().filter(u -> u.getEmployee().equals(employee)).collect(Collectors.toList());
		return employeeTimeTables;
	}
	
	public TimeTable getTimeTablesByDateAndEmployee(Employee employee, Calendar date) {
		List<TimeTable> employeeTimeTables = timeTables.stream().filter(u -> u.getEmployee().equals(employee)).collect(Collectors.toList());
		TimeTable finalTimeTable = employeeTimeTables.stream().filter(u -> u.getDate().equals(date)).findAny().orElse(null);
		return finalTimeTable;
	}
	
	public void editTimeTable(Activity activity, Employee employee, Calendar date, int workHours) {
		TimeTable timeTable = getTimeTablesByDateAndEmployee(employee, date);
		timeTable.editActivity(activity);
		timeTable.editEmployee(employee);
		timeTable.editDate(date);
		timeTable.editHours(workHours);
	}
	

	public void generateStatusReport() {
		double sumExpectedHours = 0;
		double sumWorkedHours = 0; // Total worked hours on the project
		for(Activity a : activities){
			sumExpectedHours += a.getExpectedHours();
			sumWorkedHours += a.getWorkedHours();
		}	
	}
}
