package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class Project {
	private String projectName;
	private double expectedHours;
	private Calendar startDate, endDate;
	
	private Boolean ongoingProject = false;
	
	private List<Employee> employees = new ArrayList<>();
	private List<Activity> activities = new ArrayList<>();
	private List<TimeTable> timeTables = new ArrayList<>();
	private IDServer idServer = new IDServer();
	private Employee projectManager;
	private boolean hasProjectManager;
	private int projectID;
	
	public Project(String projectName, Double expectedHours, Calendar startDate, Calendar endDate) {
		this.projectName = projectName;
		this.expectedHours = 0 > expectedHours ? 0 : expectedHours - expectedHours % 0.5;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectID = idServer.generateID(startDate);
		addBaseActivities();
	} 
	// [HANS] 
	private void addBaseActivities() {
		Calendar dates = new GregorianCalendar();
		
		Activity vacation = new Activity(this.getProjectID(), "Vacation", dates, dates);
		Activity sickdays = new Activity(this.getProjectID(), "Sickdays", dates, dates);
		Activity courses = new Activity(this.getProjectID(), "Courses", dates, dates);
		activities.add(vacation);
		activities.add(sickdays);
		activities.add(courses);
	}
	
	// [HANS] Get project name
	public String getProjectName() {
		return projectName;
	}
	
	// [HANS] Check if project manager assigned
	public boolean hasProjectManager() {
		return hasProjectManager;
	}
	
	// [HANS] Get project manager
	public Employee getProjectManager() {
		return projectManager;
	}
	
	// [HANS] Get project id
	public int getProjectID() {
		return projectID;
	}
	
	// [HANS] Get start date
	public Calendar getStartDate() {
		return startDate;
	}
	
	// [HANS] Get end date
	public Calendar getEndDate() {
		return endDate;
	}
	
	// [HANS] See if the project is active or closed
	public boolean getOngoingProject() {
		return ongoingProject;
	}
	
	// [HANS] Get employees working on this project
	public List<Employee> getEmployeesAssignedToProject(){
		return employees;
	}
	
	// [HANS] Get activities in this project
	public List<Activity> getActivites(){
		return activities;
	}
	
	// [HANS] Get expected hour of project
	public double getExpectedHours() {
		return expectedHours;
	}
	
	// [HANS] Get worked hours on this project
	public double getWorkedHours(){
		double sumWorkedHours = 0;
		for(Activity a : activities){
			sumWorkedHours += a.getWorkedHours();
		}
		return sumWorkedHours;
	}		
	
	// [THOR] Get time tables associated with this project
	public List<TimeTable> getTimeTables(){
		return this.timeTables;
	}
	
	// [THOR] Get time tables of all employees working on this project
	public List<Employee> getEmployeesFromTimeTable(Activity a){
		List<Employee> es = new ArrayList<>(a.getEmployees());
		for(TimeTable t : this.timeTables) {
			if(!es.contains(t.getEmployee()) /*&& (t.getHoursWorked() != 0)*/ && (t.getActivity().equals(a))) {
				es.add(t.getEmployee());
			}
		}
		return es;
	}

	// [THOR] Get time tables associated with an employee working on this project
	public List<TimeTable> getTimeTablesByEmployee(Employee employee) {
		List<TimeTable> employeeTimeTables = timeTables.stream().filter(u -> u.getEmployee().equals(employee)).collect(Collectors.toList());
		return employeeTimeTables;
	}
		
	// [THOR] 
	public List<TimeTable> getNumberOfTimeTablesByDateAndEmployee(Employee employee, Calendar date) {
		List<TimeTable> employeeTimeTables = timeTables.stream().filter(u -> u.getEmployee().equals(employee)).collect(Collectors.toList());
		List<TimeTable> finalTimeTable = employeeTimeTables.stream().filter(u -> u.getDate().get(Calendar.WEEK_OF_YEAR) == date.get(Calendar.WEEK_OF_YEAR)).collect(Collectors.toList());
		
		return finalTimeTable;
	}
	
	// [THOR] 
	// Get time tables for a specific employees and a specific activity
	public List<TimeTable> getTimeTableForEmployeeAndActivity(Employee employee, Activity activity, List<TimeTable> timeTableInput){
		List<TimeTable> list = new ArrayList<>();
		list = getTimeTableForEmployee(employee, timeTableInput);
		list = getTimeTableForActivity(activity, list);
		return list;
	}
	
	// [NIIKAS] Edit project name
	public void editProjectName(String projectName){
		this.projectName = projectName;
	}
	
	// [NIIKAS] Edit expected hours on project
	public void editExpectedHours(Double expectedHours) {
		this.expectedHours = 0 > expectedHours ? 0 : expectedHours - expectedHours % 0.5 ;
	}
	
	// [NIIKAS] Edit start date of project
	public void editStartDate(Calendar newStartDate) throws OperationNotAllowedException {
		if(newStartDate.compareTo(this.endDate) > 0) {
			throw new OperationNotAllowedException("Start date is after end date");
		}
		this.startDate = newStartDate;
	}
	
	// [NIIKAS] Edit end date of project
	public void editEndDate(Calendar newEndDate) throws OperationNotAllowedException {
		if(newEndDate.compareTo(this.startDate) < 0) {
			throw new OperationNotAllowedException("End date is before start date");
		}
		this.endDate = newEndDate;
	}
	
	// [NIIKAS] Set project as active (true)
	public void beginProject() {
		ongoingProject = true;
	}
	
	// [NIIKAS] set project as inactive (false)
	public void closeProject()  {
		ongoingProject = false;
	}
	
	// [MALTHE] Override and assign the project manager for this project, even if already assigned
	public void setProjectManager(Employee employee) { 
		this.projectManager = employee;
		this.hasProjectManager = true;
	}
	
	// [MALTHE] Remove current project manager
	public void removeProjectManager(){
		this.projectManager = null;
		this.hasProjectManager = false;
	}
	
	// [MALTHE] Promote an employee part of this project to project manager
	// Throws Exception if project manager is already assigned or employee is not part of project
	public void promoteEmployee(String id) throws OperationNotAllowedException{
		if(this.hasProjectManager) {
			throw new OperationNotAllowedException("Project already has Project Manager");
		}
		
		for(Employee e : employees){
			if(e.getId().equals(id) == true){
				setProjectManager(e);
				return;
			}
		}
		
		throw new OperationNotAllowedException("Employee is not part of the project");
	}
	
	// [MALTHE] Add employee - Throws Exception if employee is already part of project
	public void addEmployee(Employee employee) throws OperationNotAllowedException { 
		if(!employees.contains(employee)){
			employees.add(employee);
		} else {
			throw new OperationNotAllowedException("Employee already part of project");
		}
	}
	
	// [MALTHE] Remove employee - Throws Exception if employee is not part of project
	public void removeEmployee(Employee employee) throws OperationNotAllowedException {
		if(employees.contains(employee)){
			employees.remove(employee);
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
	
	// [MALTHE] Create an activity for project 
	// Throws exception if activity is unnamed or if another activity in this project is named the same
	public void createActivity(String description) throws OperationNotAllowedException {
		
		if(description.equals("")) {
			throw new OperationNotAllowedException("Activities must have a name");
		}
		
		Activity a = activities.stream().filter(u -> u.getDescription().equals(description)).findAny().orElse(null);
		
		
		if(a != null){
			throw new OperationNotAllowedException("Activities must have a unique name");
		}

		Activity activity = new Activity(projectID, description, startDate, endDate);
		addActivity(activity);
	}
	
	// [MALTHE] Add activity - Throws Exception if activity is already part of project
	public void addActivity(Activity activity) throws OperationNotAllowedException { 
		
		if(!activities.contains(activity)){
			activities.add(activity);
		} else {
			throw new OperationNotAllowedException("Activity already part of project");
		}
	}
	
	// [MALTHE] Remove activity - Throws Exception if activity is not part of project
	public void removeActivity(String employeeId, Activity activity) throws OperationNotAllowedException {
		Employee e = findEmployeeById(employeeId);
		
		checkAuth(e);
		
		if(!activities.contains(activity)) {
			throw new OperationNotAllowedException("activity is not part of project");
		}
		activities.remove(activity);
	}
	
	// [NIKLAS]
	public boolean findEmployee(Employee employee) throws OperationNotAllowedException {
		for(Employee e : employees){
			if(e.equals(employee) == true){
				return true;
			}
		}
		return false;
	}
	
	// [NIIKAS] Find a specific activity associated with this project by it's description
	public Activity findActivityByDescription(String description) throws OperationNotAllowedException {
		Activity activity = activities.stream().filter(u -> u.getDescription().equals(description)).findAny().orElse(null);
	
		if(activity == null){
			throw new OperationNotAllowedException("Activity does not exist");
		}
		return activity;
	}
	
	// [NIIKAS] Add an employee to specific activity associated wit this project
	public void addEmployeeToActivity(Employee employee, String description) throws OperationNotAllowedException{
		Activity a = findActivityByDescription(description);
		if(findEmployee(employee)){
			a.addEmployee(employee);
		}
	}
	
	// [NIIKAS] Remove an employee from specific activity associated with this project
	public void removeEmployeeFromActivity(Employee employee, String description) throws OperationNotAllowedException{
		Activity a = findActivityByDescription(description);
		if(findEmployee(employee)){
			a.removeEmployee(employee);
		}
	}
	
	// [THOR] Add worked hours to an activity
	public void addHoursToActivity(Activity activity, Employee employee, double hours){
		activity.addWorkedHours(hours);
		TimeTable timeTable = new TimeTable(activity, employee, hours);
		timeTables.add(timeTable);
	}
	
	// [NIKLAS] Removes all activities from project
	public void clean() throws OperationNotAllowedException {
		for(Activity activity : activities){
			for(Employee employee : employees){
				activity.removeEmployee(employee);
				}
		}
		activities.clear();
		employees.clear();
	}
	
	
	// [NIKLAS] When searching with a key word these projects appear:
	public boolean match(String searchText) {
		String SearchText = searchText.toLowerCase();
		
		if(this.hasProjectManager && projectManager.getName() != null) {
			return projectName.toLowerCase().contains(SearchText) || projectManager.getName().toLowerCase().contains(SearchText) || projectManager.getId().toLowerCase().contains(SearchText);
		} else if (this.hasProjectManager) {
			return projectName.toLowerCase().contains(SearchText) || projectManager.getId().toLowerCase().contains(SearchText.toLowerCase());
		} else {
			return projectName.toLowerCase().contains(SearchText);
		}
	}
	
	// [NIKLAS] Display on GUI what comes up when we search for a key word
	public String toString() {
		String name = this.getProjectName().isBlank() ? "No project name yet" : this.getProjectName();
		return "Project ID: " + String.valueOf(getProjectID()) + " - " + name;
	}
	
	private void checkAuth(Employee e) throws OperationNotAllowedException {
		if(e == null){
			throw new OperationNotAllowedException("Employee is not part of project");
		}
		
		if(hasProjectManager() && !e.equals(projectManager)){
			throw new OperationNotAllowedException("Project Manager login required");
		}
	}
	
	private Employee findEmployeeById(String id){
		return employees.stream().filter(u -> u.getId().equals(id)).findAny().orElse(null);
	}
	
	// [THOR] Private method to get time tables for a certain employee
	private List<TimeTable> getTimeTableForEmployee(Employee employee, List<TimeTable> timeTableInput){
		List<TimeTable> timeTablesWithEmployee = new ArrayList<>();
		
		for(TimeTable t : timeTableInput) {
			if(t.getEmployee().equals(employee)) {
				timeTablesWithEmployee.add(t);
			}
		}
		return timeTablesWithEmployee;
	}
	
	// [THOR] Private method to get time tables for a certain activity
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
