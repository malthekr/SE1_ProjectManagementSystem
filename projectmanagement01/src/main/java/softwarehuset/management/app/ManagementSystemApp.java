package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManagementSystemApp {
	private boolean adminLoggedIn = false;
	private List<Project> projectRepository = new ArrayList<>();
	private List<Employee> Employees = new ArrayList<Employee>();
	private DateServer dateServer = new DateServer(); 
	//public ManagementSystem (){}

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
	
	public void addProject(Project project) throws OperationNotAllowedException {
		checkAdminLoggedIn();
		projectRepository.add(project);
	}
	
	public void addEmployee(Employee Employee) throws OperationNotAllowedException {
		checkAdminLoggedIn();
		
		if(containsEmployeeWithId(Employee.getId())){
			throw new OperationNotAllowedException("Employee ID already taken");
		}
		
		Employees.add(Employee);
	}
	
	public void checkAdminLoggedIn() throws OperationNotAllowedException {
		if (!adminLoggedIn) {
			throw new OperationNotAllowedException("Administrator login required");
		}
	}

	public Employee FindEmployeeById(String id){
		return Employees.stream().filter(u -> u.getId().equals(id)).findAny().orElse(null);
	}
	
	public boolean containsEmployeeWithId(String id){
		for(Employee e : Employees){
			if(e.getId().equals(id)) { return true;}
		}
		
		return false;
	}

	public void setDateServer(DateServer dateServer) {
		this.dateServer = dateServer;		
	}
	
	public void createProject(Project project) throws OperationNotAllowedException {
		checkAdminLoggedIn();
		projectRepository.add(project);
	}
	

	
}

