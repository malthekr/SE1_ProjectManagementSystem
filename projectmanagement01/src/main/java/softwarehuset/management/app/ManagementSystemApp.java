package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ManagementSystemApp {
	private boolean adminLoggedIn = false;
	private boolean employeeLoggedIn = false;
	private Employee employeeLoggedInId;
	private List<Project> projectRepository = new ArrayList<>();
	private List<Employee> Employees = new ArrayList<Employee>();
	private DateServer dateServer = new DateServer(); 
	//public ManagementSystem (){}
	private final ConcurrentHashMap<Integer, AtomicInteger> counters = new ConcurrentHashMap<>();

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
	
	public Project FindProjectById(int id) throws OperationNotAllowedException{
		Project project = projectRepository.stream().filter(u -> u.getProjectID() == (id)).findAny().orElse(null);
		
		//if(project == null){
		//	throw new OperationNotAllowedException("Project ID does not exist");
		//}
		
		return project;
	}
	
	public void addEmployeeToProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(EmployeeId);
		Project project = FindProjectById(ProjectId);
		
		if(adminLoggedIn) {
			project.addEmployee(employee);
			return;
		}
		
		if(employeeLoggedIn) {
			if(!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException("Employee has to be Project Manager to add employees");
			}
			project.addEmployee(employee);
			return;
		}
		
		throw new OperationNotAllowedException("Admin or Project Manager log in required");
	}
	
	public boolean containsEmployeeWithId(String id){
		for(Employee e : Employees){
			if(e.getId().equals(id)) { return true;}
		}
		
		return false;
	}
	
	public boolean checkIfEmployeeIsPartOfProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(EmployeeId);
		Project project = FindProjectById(ProjectId);
		
		return project.findEmployee(employee);
		
	}
	
	public boolean checkIfProjectHasPM(int ProjectId) throws OperationNotAllowedException {
		Project project = FindProjectById(ProjectId);
		return project.getProjectManager() != null ? true : false;
	}
	
	public void removeEmployeeWithIdFromProject(int ProjectId, String EmployeeId) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(EmployeeId);
		Project project = FindProjectById(ProjectId);
		
		project.removeEmployee(employee);
	}

	public void setDateServer(DateServer dateServer) {
		this.dateServer = dateServer;		
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
	
	public void closeProject(Project project) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		project.closeProject();
	}
	
	public void promoteToPm(int projectId, String Id) throws OperationNotAllowedException {
		Project project = FindProjectById(projectId);
		
		//if(!adminLoggedIn) {
		//	throw new OperationNotAllowedException("Administrator login required");
		//}
		
		project.promoteEmployee(Id);
	}
	
	public void editProjectName(int projectId, String employeeId, String projectName) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(employeeId);
		Project project = FindProjectById(projectId);
		
		if(employeeLoggedIn) {
			if(!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException("Employee has to be Project Manager to change project name");
			}
			project.editProjectName(projectName);
			return;
		}
		
		throw new OperationNotAllowedException("Project Manager log in required");
		
	}
	
	public void editStartDate(int projectId, String employeeId, int days) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(employeeId);
		Project project = FindProjectById(projectId);
		
		if(employeeLoggedIn) {
			if(!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException("Employee has to be Project Manager to change project start date");
			}
			Calendar startDate = project.getStartDate();
			startDate.add(Calendar.DAY_OF_YEAR, days);
			project.editStartDate(startDate);
			return;
		}
		
		throw new OperationNotAllowedException("Project Manager log in required");
	}
	
	public void editEndDate(int projectId, String employeeId, int days) throws OperationNotAllowedException {
		Employee employee = FindEmployeeById(employeeId);
		Project project = FindProjectById(projectId);
		
		if(employeeLoggedIn) {
			if(!employeeLoggedInId.equals(project.getProjectManager())) {
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
		Project project = FindProjectById(projectId);
		Calendar Datee = date;
		Datee.add(Calendar.DAY_OF_YEAR, days);
		return project.getStartDate() == Datee ? true : false;
	}
	
	public boolean CheckifEndDateMoved(int projectId, int days, Calendar date) throws OperationNotAllowedException {
		Project project = FindProjectById(projectId);
		Calendar Datee = date;
		Datee.add(Calendar.DAY_OF_YEAR, days);
		return project.getEndDate() == Datee ? true : false;
	}
	
	public void createActivity(int projectId, String employeeId, String description) throws OperationNotAllowedException {
		Project project = FindProjectById(projectId);
		Employee employee = FindEmployeeById(employeeId);
		
		if(employeeLoggedIn && project.hasProjectManager()) {
			if(!employeeLoggedInId.equals(project.getProjectManager())) {
				throw new OperationNotAllowedException("Employee has to be Project Manager to change project end date");
			}
			project.createActivity(description, employee);
			return;
		}
		
		if(adminLoggedIn()) {
			project.createActivity(description, employee);
			return;
		}
		
		throw new OperationNotAllowedException("Admin or Project Manager log in required");
	}
	
	public Activity findActivityByDescription(int projectId, String description) throws OperationNotAllowedException {
		Project project = FindProjectById(projectId);
		return project.findActivityByDescrption(description);
	}
	
	public int generateID(int year) {
		AtomicInteger counter = counters.computeIfAbsent(year, y -> new AtomicInteger(0));
        int number = counter.incrementAndGet();
        int id = year * 1000 + number;
		return id;
	}
	
	// Incase we delete a project and want to reuse serial
	public void ReuseSerialNumber(Project project) {
        int year = project.getProjectID() / 1000;
        int number = project.getProjectID() % 1000;
        counters.get(year).decrementAndGet();
    }
}

