package softwarehuset.management.app;

import java.util.Observable;

public class LoginSystem extends Observable {
	private EmployeeRepository employeeRepository;
	
	private boolean adminLoggedIn = false;
	private boolean employeeLoggedIn = false;
	private String currentEmployeeLoggedInID = null;
	
	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public boolean adminLoggedIn() {
		return adminLoggedIn;
	}
	
	public boolean employeeLoggedIn() {
		return employeeLoggedIn;
	}
	
	public String getCurrentLoggedID() {
		return currentEmployeeLoggedInID;
	}
	
	public void employeeLogin(String id) throws OperationNotAllowedException {
		if(employeeRepository.checkIfEmployeeExists(id)) {
			employeeLoggedIn = true;
			currentEmployeeLoggedInID = id;
			setChanged();
			notifyObservers();
		} else {
			throw new OperationNotAllowedException("Employee ID does not exist");
		}
	}
	
	public void employeeLogOut() {
		employeeLoggedIn = false;
		currentEmployeeLoggedInID = null;
		setChanged();
		notifyObservers();
	}
	
	// Admin login with password "admi"
	public void adminLogin(String id) throws OperationNotAllowedException {
		if(id.equals("admi")) {
			adminLoggedIn = true;
			setChanged(); 
			notifyObservers();
		} else {
			throw new OperationNotAllowedException("Incorrect admin ID");
		}
	}
	
	// Admin logs out
	public boolean adminLogout() {
		adminLoggedIn = false;
		setChanged();
		notifyObservers();
		return adminLoggedIn;
	}	
	
	public void checkAdminLoggedIn() throws OperationNotAllowedException {
		if(!adminLoggedIn) {
			throw new OperationNotAllowedException("Admin login required");
		}
	}
	
	public void checkEmployeeLoggedIn() throws OperationNotAllowedException {
		if(!employeeLoggedIn) {
			throw new OperationNotAllowedException("Employee login required");
		}
	}
	
}