package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import softwarehuset.management.app.Employee;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.LoginSystem;
import softwarehuset.management.app.EmployeeRepository;

import softwarehuset.management.app.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginLogoutSteps {

	private ManagementSystemApp managementSystem;
	private String id;
	private ErrorMessageHolder errorMessageHolder;
	private LoginSystem loginSystem = managementSystem.getLoginSystem();
	private EmployeeRepository employeeRepository = managementSystem.getEmployeeRepository();
	
	public LoginLogoutSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
	}
	
	@Given("admin is not logged in")
	public void thatTheAdministratorIsNotLoggedIn() {
		assertFalse(loginSystem.adminLoggedIn());
	}
	
	@Given("admin id is {string}")
	public void theIdIs(String id) {
		this.id = id;
	}
	
	@Then("admin login succeeds")
	public void admingLogin() throws OperationNotAllowedException {
		loginSystem.adminLogin(id);
		assertTrue(loginSystem.adminLoggedIn());
	}
	
	@Given("admin is logged in")
	public void adminLoggedIn() {
		assertTrue(loginSystem.adminLoggedIn());
	}
	
	@When("admin logs out")
	public void adminLogout() {
		loginSystem.adminLogout();
	}
	
	@Then("admin login fails")
	public void adminLoginFail() {
		assertFalse(loginSystem.adminLoggedIn());
	}
	
	@Given("admin is already logged in")
	public void thatTheAdminIsLoggedIn() throws OperationNotAllowedException {
		loginSystem.adminLogin("admi");
		assertTrue(loginSystem.adminLoggedIn());
	}
	
	@Given("employee with ID {string} is logged in")
	public void employeeWithIDIsLoggedIn(String id) throws OperationNotAllowedException{
	    createEmployee(id);
	    loginSystem.employeeLogin(id);
	    assertTrue(loginSystem.employeeLoggedIn());
	}
	
	@Given("employee with ID {string} exists")
	public void employeeWithIDExists(String Id) throws OperationNotAllowedException{
	    createEmployee(Id);
	    assertNotNull(employeeRepository.findEmployeeByID(Id));
	}
	
	@Given("admin is logged out")
	public void adminIsLoggedOut() {
		assertFalse(loginSystem.adminLogout());
	}
	
	@Given("employee {string} is logged out")
	public void employeeIsLoggedOut(String Id) {
		loginSystem.employeeLogOut();
	    assertFalse(loginSystem.employeeLoggedIn());
	}
	
	@When("{string} logs in")
	public void logsIn(String employeeId) {
		try {
			loginSystem.employeeLogin(employeeId);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}		
	}
	
	@Then("employee is logged in")
	public void isLoggedIn() {
		assertTrue(loginSystem.employeeLoggedIn());
	}
	
	private void createEmployee(String Id) throws OperationNotAllowedException {
		loginSystem.adminLogin("admi");
		Employee employee = new Employee(Id);
		employeeRepository.addEmployee(employee);
		loginSystem.adminLogout();
	}
}