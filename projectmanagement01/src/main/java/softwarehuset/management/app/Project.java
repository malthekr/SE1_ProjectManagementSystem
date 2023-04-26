package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Project {
	
	private String projectName;
	private Double expectedHours;
	private Calendar startDate, endDate;
	private Boolean ongoingProject = false;
	private List<Employee> employeesAssignedToProject = new ArrayList<>();
	private List<Activity> activities = new ArrayList<>();
	private Employee projectManager;
	private int projectId;
	
	public Project(String projectName, Double expectedHours, Calendar startDate, Calendar endDate, int projectId) {
		this.projectName = projectName;
		this.expectedHours = expectedHours;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectId = projectId;
	}
	
	public Project(Double expectedHours, Calendar startDate, Calendar endDate, int projectId) {
		this.expectedHours = expectedHours;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectId = projectId;
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
	
	public void createActivity(String description, Employee employee) throws OperationNotAllowedException {
		if(!employeesAssignedToProject.contains(employee)) {
			throw new OperationNotAllowedException("Employee is not part of the project");
		}
		Activity activity = new Activity(projectId, employee, description, startDate, endDate);
		activities.add(activity);
		employee.addActivity(activity);
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
		return projectId;
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
	
	//Så hvad er close project?
	public void closeProject() {
		ongoingProject = false;
	}

	public void promoteEmployee(String id) throws OperationNotAllowedException{
		Boolean assigned = false;
		
		if(projectManager != null) {
			throw new OperationNotAllowedException("Project already has Project Manager");
		}
		
		for(Employee e : employeesAssignedToProject){
			if(e.getId().equals(id) == true){
				assigned = true;
				setProjectManager(e);
			}
		}
		
		if(!assigned){
			throw new OperationNotAllowedException("Employee is not part of the project");
		}
	}
	
	public boolean findEmployee(Employee employee) throws OperationNotAllowedException {
		for(Employee e : employeesAssignedToProject){
			if(e.equals(employee) == true){
				return true;
			}
		}
		return false;
	}
	
	public Activity findActivityByDescrption(String description) {
		for(Activity activity : activities) {
			if(activity.getDescription().equals(description)) {
				return activity;
			}
		}
		return null;
	}
}
