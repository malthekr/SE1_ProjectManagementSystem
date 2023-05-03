package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ManagementSystemApp extends Observable {
	private boolean adminLoggedIn = false;
	private boolean employeeLoggedIn = false;
	private Employee employeeLoggedInId;
	private List<Project> projectRepository = new ArrayList<>();
	private List<Employee> Employees = new ArrayList<Employee>();
	private DateServer dateServer = new DateServer();

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

	public boolean employeeLogin(String id) throws OperationNotAllowedException {
		if (!containsEmployeeWithId(id)) {
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

	public void addEmployee(Employee employee) throws OperationNotAllowedException {
		if (!adminLoggedIn) {
			throw new OperationNotAllowedException("Administrator login required");
		}

		if (containsEmployeeWithId(employee.getId())) {
			throw new OperationNotAllowedException("Employee ID already taken");
		}

		Employees.add(employee);
	}

	// Removes employee from the system
	public void removeEmployee(Employee employee) throws OperationNotAllowedException {
		if (!adminLoggedIn()) {
			throw new OperationNotAllowedException("Administrator login required");
		}
		/*
		 * if(employee.getActivities() != null) {
		 * removeEmployeeFromAllActivites(employee.getId());
		 * }
		 */
		// for(Activity a : )

		/*
		 * for(Project p : projectRepository) {
		 * if(p.getEmployeesAssignedToProject().contains(employee)) {
		 * p.removeEmployee(employee);
		 * }
		 * //removeEmployeeWithIdFromProject(p.getProjectID(), employee.getId());
		 * }
		 */

		for (Project p : projectRepository) {
			if (p.getEmployeesAssignedToProject().contains(employee)) {
				p.removeEmployee(employee);
			}
		}

		Employees.remove(employee);
	}

	public void removeEmployeeFromAllActivites(String employeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(employeeId);
		List<Activity> activities = employee.getActivities();

		if (adminLoggedIn() && activities != null) {
			for (Activity activity : activities) {
				activity.removeEmployee(employee);
			}
		}
		return;
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

	public Employee FindEmployeeById(String id) throws OperationNotAllowedException {
		Employee employee = Employees.stream().filter(u -> u.getId().equals(id)).findAny().orElse(null);

		if (employee == null) {
			throw new OperationNotAllowedException("Employee ID does not exist");
		}
		return employee;
	}

	public Project findProjectById(int id) throws OperationNotAllowedException {
		Project project = projectRepository.stream().filter(u -> u.getProjectID() == (id)).findAny().orElse(null);
		if (project == null) {
			throw new OperationNotAllowedException("Project Id does not exist");
		}
		return project;
	}

	public void addEmployeeToProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(EmployeeId);
		Project project = findProjectById(ProjectId);

		if (adminLoggedIn) {
			project.addEmployee(employee);
			return;
		}

		if (employeeLoggedIn) {
			if (!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException("Employee has to be Project Manager to add employees");
			}
			project.addEmployee(employee);
			return;
		}

		throw new OperationNotAllowedException("Admin or Project Manager log in required");
	}

	public boolean containsEmployeeWithId(String id) {
		for (Employee e : Employees) {
			if (e.getId().equals(id)) {
				return true;
			}
		}

		return false;
	}

	public boolean checkIfEmployeeIsPartOfProject(int ProjectId, String EmployeeId)
			throws OperationNotAllowedException {
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

		project.removeEmployee(employee);
	}

	public boolean checkIfUniqueProjectId(int Id) {
		int num = 0;
		for (Project i : projectRepository) {
			if (i.getProjectID() == Id) {
				num++;
			}
		}
		return (num == 1);
	}

	public void closeProject(Project project) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		project.closeProject();
	}

	public void promoteToPm(int projectId, String Id) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);

		// if(!adminLoggedIn) {
		// throw new OperationNotAllowedException("Administrator login required");
		// }

		project.promoteEmployee(Id);
	}

	public void editProjectName(int projectId, String projectName) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);

		if (employeeLoggedIn) {
			if (!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException("Employee has to be Project Manager to change project name");
			}
			project.editProjectName(projectName);
			return;
		}

		throw new OperationNotAllowedException("Project Manager log in required");

	}

	public void editStartDate(int projectId, int days) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);

		if (employeeLoggedIn) {
			if (!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException(
						"Employee has to be Project Manager to change project start date");
			}
			Calendar startDate = project.getStartDate();
			startDate.add(Calendar.DAY_OF_YEAR, days);
			project.editStartDate(startDate);
			return;
		}

		throw new OperationNotAllowedException("Project Manager log in required");
	}

	public void editEndDate(int projectId, int days) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);

		if (employeeLoggedIn) {
			if (!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException("Employee has to be Project Manager to change project end date");
			}
			Calendar endDate = project.getEndDate();
			endDate.add(Calendar.DAY_OF_YEAR, days);
			project.editEndDate(endDate);
			return;
		}

		throw new OperationNotAllowedException("Project Manager log in required");
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

		if (employeeLoggedIn && project.hasProjectManager()) {
			if (!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException("Only Project Manager can add activities");
			}
			project.createActivity(description);
			return;
		}
		if (employeeLoggedIn && !project.hasProjectManager()) {
			project.createActivity(description);
			return;
		}
		if (adminLoggedIn()) {
			project.createActivity(description);
			return;
		}

		throw new OperationNotAllowedException("Admin or Project Manager log in required");
	}

	public Activity findActivityByDescription(int projectId, String description) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		return project.findActivityByDescrption(description);
	}

	public void UpdateExpectedHours(int projectId, double hours) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);

		if (employeeLoggedIn) {
			if (!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException("Only Project Managers are allowed to set expected hours");
			}
			project.editExpectedHours(hours);
			return;
		}
	}

	public void UpdateEndDate(int dd, int mm, int yyyy, int projectId, String description)
			throws OperationNotAllowedException {
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

	public void setActivityDescrption(Project project, String description1, String description2)
			throws OperationNotAllowedException {
		if (checkAuth(project)) {
			project.findActivityByDescription(description1).setDescrption(description2);
		}
	}

	public void generateStatusReport(int projectId) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);
		if (checkAuth(project)) {
			project.generateStatusReport();

		}
	}

	// Add employee to activity in project
	public void addEmployeeToActivity(Employee employee, Project project, String description)
			throws OperationNotAllowedException {
		// Adds employee to project employee list
		project.addEmployeeToActivity(employee, description);

		// Adds activity to employee project-activity list
		employee.addActivity(project, project.findActivityByDescription(description));
	}

	// Remove employee from activity in project
	public void removeEmployeeFromActivity(Employee employee, Project project, String description)
			throws OperationNotAllowedException {
		// Remove employee from activity employee list
		Activity activity = project.findActivityByDescription(description);
		activity.removeEmployee(employee);

		// Remove activity from employee project-activity list
		employee.removeActivity(project, project.findActivityByDescription(description));
	}

	public List<Activity> getActivities(int projectId, Employee anotherEmployee) throws OperationNotAllowedException {
		Project project = findProjectById(projectId);

		if (checkAuth(project)) {
			return anotherEmployee.listOfActivitiesInProject(project);
		}

		throw new OperationNotAllowedException("addEmployeeToActivity error");
	}

	private boolean checkAuth(Project project) throws OperationNotAllowedException {
		if (employeeLoggedIn && project.hasProjectManager()) {
			if (!employeeLoggedInId.equals(project.getProjectManager())) {
				// throws error if employee logged in is not PM
				throw new OperationNotAllowedException("Project Manager login required");
			}
			return true; // returns true PM is logged in
		}
		if (project.findEmployee(employeeLoggedInId) && !project.hasProjectManager()) {
			return true; // returns true if project has no PM and employee (who is part of project) is
							// logged in
		}
		if (adminLoggedIn()) {
			return true;
		}
		// return false;
		throw new OperationNotAllowedException("Project Manager login required");
	}

	// Removes activity from project and employees
	public void removeActivity(Activity activity) throws OperationNotAllowedException {
		Project project = findProjectById(activity.getProjectId());
		List<Employee> e = activity.getEmployees();

		if (checkAuth(project)) {
			for (Employee em : e) {
				em.removeActivity(project, activity);
			}
			project.removeActivity(activity);
		}
	}

	// Remove project from repository
	public void removeProject(Project project) throws OperationNotAllowedException {
		List<Employee> empl = project.getEmployeesAssignedToProject();

		if (adminLoggedIn() || currentEmployee().equals(project.getProjectManager())) {
			for (Employee e : empl) {
				for (Activity a : e.getActivities())
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
		List<Activity> activites = new ArrayList<>();

		for (Project p : projectRepository) {
			activites.addAll(p.getActivites().stream()
					.filter(b -> b.match(searchText))
					.collect(Collectors.toList()));
		}

		setChanged();
		notifyObservers();
		return activites;
	}

	public void addHourToActivity(Activity activity, double hours) throws OperationNotAllowedException {
		Project project = findProjectById(activity.getProjectId());
		if (checkAuth(project)) {
			// activity.addWorkedHours(hours);

			project.addHoursToActivity(activity, currentEmployee(), hours);
		}
	}

	public void editExpectedHoursActivity(Activity activity, Double hours) throws OperationNotAllowedException {
		Project project = findProjectById(activity.getProjectId());
		if (checkAuth(project)) {
			activity.setExpectedHours(hours);
		}
	}

	public void editProjectActivityDescription(Activity activity, String description)
			throws OperationNotAllowedException {
		Project project = findProjectById(activity.getProjectId());
		if (checkAuth(project)) {
			activity.setDescrption(description);
		}
	}

	public void getStatus(Activity activity) throws OperationNotAllowedException {
		Project project = findProjectById(activity.getProjectId());
		List<TimeTable> timeTable = project.getTimeTablesByEmployee(currentEmployee());
		// System.out.println(timeTable.size());

		for (TimeTable t : timeTable) {
			System.out.println(t.getActivity() + " " + t.getHoursWorked());
		}
	}

	/*
	 * public void addEmployeeToActivity(Project project, Employee employee, String
	 * description) throws OperationNotAllowedException {
	 * // Adds employee to project employee list
	 * project.addEmployeeToActivity(employee, description);
	 * 
	 * // Adds activity to employee project-activity list
	 * Activity activity = project.findActivityByDescription(description);
	 * employee.addActivity(project, activity);
	 * }
	 */

	public void exampleDate() throws OperationNotAllowedException {
		adminLogin("admi");
		
		Employee employee1 = new Employee("malthe", "mkr");
		Employee employee2 = new Employee("niklas", "nik");
		Employee employee3 = new Employee("hans", "hans");
		Employee employee4 = new Employee("Reub", "reb");
		Employee employee5 = new Employee("Kjøl", "øl");
		Employee employee6 = new Employee("mor", "mor");
		Employee employee7 = new Employee("DTU", "DTUT");
		Employee employee8 = new Employee("mors", "lort");
		
		
		addEmployee(employee1);
		addEmployee(employee2);
		addEmployee(employee3);
		
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
		
		Project project1 = new Project("GUI", 10.0, startDate, endDate1);
		Project project2 = new Project("SERIOUS STUFF", 13.0, startDate, endDate2);
		Project project3 = new Project("Example??", 5.553, startDate, endDate3);
		Project project4 = new Project("Programmering", 100.23, startDate, endDate3);
		
		createProject(project1);
		createProject(project2);
		createProject(project3);
		createProject(project4);
		
		project1.addEmployee(employee1);
		project1.setProjectManager(employee1);
		project1.addEmployee(employee2);
		project1.addEmployee(employee3);
		
		project2.addEmployee(employee1);
		project2.addEmployee(employee2);
		project2.addEmployee(employee3);
		
		project3.addEmployee(employee1);
		
		project1.createActivity("Act1");
		project1.createActivity("Act2");
		
		project2.createActivity("gui");
		project2.createActivity("prog");
		
		project3.createActivity("weird shit");
		project3.createActivity("horse weird");
		
		project4.createActivity("skoleting");
		project4.createActivity("gokke den af");
		
		addEmployeeToActivity(employee1, project1, "Act1");
		addEmployeeToActivity(employee2, project1, "Act1");
		addEmployeeToActivity(employee3, project1, "Act1");
		addEmployeeToActivity(employee1, project1, "Act2");
		
		addEmployeeToActivity(employee3, project2, "gui");
		addEmployeeToActivity(employee4, project2, "prog");
		addEmployeeToActivity(employee1, project2, "prog");
		
		addEmployeeToActivity(employee2, project3, "weird shit");
		addEmployeeToActivity(employee1, project4, "skoleting");
		
		adminLogout();
	}

	throw new OperationNotAllowedException("Only Project Managers are allowed to set expected hours");
}}
