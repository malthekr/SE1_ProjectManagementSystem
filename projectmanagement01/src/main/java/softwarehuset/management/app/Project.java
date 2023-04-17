package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
	
	public Project(String projectName, Double expectedHours, Calendar startDate, Calendar endDate) {
		this.projectName = projectName;
		this.expectedHours = expectedHours;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	// Override and assign the project manager for this project, even if already assigned
	public void setProjectManager(Employee employee) { 
		this.projectManager = employee;
	}
	
	// Add employee - Throws Exception if employee is already part of project
	public void addEmployee(Employee employee) throws Exception { 
		if(!employeesAssignedToProject.contains(employee)){
			employeesAssignedToProject.add(employee);
		} else {
			throw new Exception("Employee already part of project");
		}
	}
	
	// Add activity - Throws Exception if activity is already part of project
	public void addActivity(Activity activity) throws Exception { 
		if(!activities.contains(activity)){
			activities.add(activity);
		} else {
			throw new Exception("Activity already part of project");
		}
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
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public Calendar getEndDate() {
		return endDate;
	}

	public void promoteEmployee(String id) throws Exception{
		Boolean assigned = false;

		for(Employee e : employeesAssignedToProject){
			if(e.getId().equals(id) == true){
				assigned = true;
			}
		}
		
		if(!assigned){
			throw new Exception("Employee is not part of the project");
		}
		
		//this.projectManager = ManagementSystem.FindEmployeeById(id);
	}
}
