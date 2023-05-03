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

	public Project(Double expectedHours, Calendar startDate, Calendar endDate) {
		this.expectedHours = expectedHours;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectID = idServer.generateID(startDate);
	}

	// Override and assign the project manager for this project, even if already
	// assigned
	public void setProjectManager(Employee employee) {
		this.projectManager = employee;
	}

	// Add employee - Throws Exception if employee is already part of project
	public void addEmployee(Employee employee) throws OperationNotAllowedException {
		if (!employeesAssignedToProject.contains(employee)) {
			employeesAssignedToProject.add(employee);
		} else {
			throw new OperationNotAllowedException("Employee already part of project");
		}
	}

	public void createActivity(String description) throws OperationNotAllowedException {
		if (description == "") {
			throw new OperationNotAllowedException("Activities must have a name");
		}
		if (activities.contains(findActivityByDescrption(description))) {
			throw new OperationNotAllowedException("Activities must have a unique name");
		}

		Activity activity = new Activity(projectID, description, startDate, endDate);
		activities.add(activity);
	}

	public void removeActivity(Activity activity) throws OperationNotAllowedException {
		if (!activities.contains(activity)) {
			throw new OperationNotAllowedException("activity is not part of project");
		}
		activities.remove(activity);
	}

	// Add activity - Throws Exception if activity is already part of project
	public void addActivity(Activity activity) throws OperationNotAllowedException {
		if (!activities.contains(activity)) {
			activities.add(activity);
		} else {
			throw new OperationNotAllowedException("Activity already part of project");
		}
	}

	public void removeEmployee(Employee employee) throws OperationNotAllowedException {
		if (employeesAssignedToProject.contains(employee)) {
			employeesAssignedToProject.remove(employee);
			if (projectManager == employee) {
				removeProjectManager();
			}

			for (Activity a : activities) {
				if (a.getEmployees().contains(employee)) {
					a.removeEmployee(employee);
				}
			}
			return;
		}

		throw new OperationNotAllowedException("Employee is not part of project");
	}

	public void removeProjectManager() {
		this.projectManager = null;
	}

	public void editProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void editExpectedHours(Double expectedHours) {
		this.expectedHours = 0 > expectedHours ? 0 : expectedHours - expectedHours % 0.5;
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

	public List<Employee> getEmployeesAssignedToProject() {
		return employeesAssignedToProject;
	}

	public List<Activity> getActivites() {
		return activities;
	}

	// Så hvad er close project?
	public void closeProject() {
		ongoingProject = false;
	}

	public void promoteEmployee(String id) throws OperationNotAllowedException {
		Boolean assigned = false;

		if (projectManager != null) {
			throw new OperationNotAllowedException("Project already has Project Manager");
		}

		for (Employee e : employeesAssignedToProject) {
			if (e.getId().equals(id) == true) {
				assigned = true;
				setProjectManager(e);

				return;
			}
		}

		if (!assigned) {
			throw new OperationNotAllowedException("Employee is not part of the project");
		}
	}

	public boolean findEmployee(Employee employee) throws OperationNotAllowedException {
		for (Employee e : employeesAssignedToProject) {
			if (e.equals(employee) == true) {
				return true;
			}
		}
		return false;
	}

	public Activity findActivityByDescrption(String description) {
		for (Activity activity : activities) {
			if (activity.getDescription().equals(description)) {
				return activity;
			}
		}
		return null;
	}

	public Object getExpectedHours() {
		return expectedHours;
	}

	public List<TimeTable> getTimeTablesByEmployee(Employee employee) {
		List<TimeTable> employeeTimeTables = timeTables.stream().filter(u -> u.getEmployee().equals(employee))
				.collect(Collectors.toList());
		return employeeTimeTables;
	}

	public TimeTable getTimeTablesByDateAndEmployee(Employee employee, Calendar date) {
		List<TimeTable> employeeTimeTables = timeTables.stream().filter(u -> u.getEmployee().equals(employee))
				.collect(Collectors.toList());
		TimeTable finalTimeTable = employeeTimeTables.stream().filter(u -> u.getDate().equals(date)).findAny()
				.orElse(null);
		return finalTimeTable;
	}

	public void editTimeTable(Activity activity, Employee employee, Calendar date, int workHours) {
		TimeTable timeTable = getTimeTablesByDateAndEmployee(employee, date);
		timeTable.editActivity(activity);
		timeTable.editEmployee(employee);
		timeTable.editDate(date);
		timeTable.editHours(workHours);
	}

	public void addHoursToActivity(Activity activity, Employee employee, double hours) {
		activity.addWorkedHours(hours);
		TimeTable timeTable = new TimeTable(activity, employee, hours);
		timeTables.add(timeTable);
	}

	public void generateStatusReport() {
		double sumExpectedHours = 0;
		double sumWorkedHours = 0; // Total worked hours on the project
		for (Activity a : activities) {
			sumExpectedHours += a.getExpectedHours();
			sumWorkedHours += a.getWorkedHours();
		}
	}

	public boolean match(String searchText) {
		if (this.hasProjectManager()) {
			return projectName.contains(searchText) || projectManager.getName().contains(searchText)
					|| projectManager.getId().contains(searchText.toLowerCase());
		} else {
			return projectName.contains(searchText);
		}
	}

	public String printDetail() {
		String name = this.getProjectName().isBlank() ? "No project name yet" : this.getProjectName();
		String pmid = !this.hasProjectManager() ? "No PM assigned yet" : this.getProjectManager().getId();
		String id = this.getEmployeesAssignedToProject() == null ? "No employees assigned yet"
				: this.getEmployeesAsString();

		StringBuffer b = new StringBuffer();
		b.append("<html>" + "<br>");
		b.append(String.format("<b>Name:</b>     %s<br>", name));
		b.append(String.format("<b>Project Id:</b>    %s<br>", this.getProjectID()));
		b.append(String.format("<b>PM:</b>     %s<br>", pmid));
		b.append(String.format("<b>Employees:</b>     %s<br>", id));
		b.append(String.format("<b>Is project Active?:</b> %s<br>", this.getOngoingProject()));
		b.append(String.format("<b>Expected Hours:</b> %s<br>", this.getExpectedHours()));
		b.append(String.format("<b>Worked Hours:</b>     %s<br>", this.getWorkedHours()));
		b.append("</html>");
		return b.toString();
	}

	public String getEmployeesAsString() {
		String name = "";
		int i = 0;

		for (Employee e : this.getEmployeesAssignedToProject()) {
			name += e.getId();
			if (i++ == this.getEmployeesAssignedToProject().size() - 1) {
				break;
			}
			name += ", ";
		}
		return name;
	}

	public String toString() {
		return "Project ID: " + String.valueOf(getProjectID());
	}
}
