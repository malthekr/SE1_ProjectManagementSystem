package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import softwarehuset.management.app.Employee;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginLogoutSteps {

	private ManagementSystemApp managementSystem;
	private String id;
	private ErrorMessageHolder errorMessageHolder;
	
	public LoginLogoutSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
	}
	
	@Given("admin is not logged in")
	public void thatTheAdministratorIsNotLoggedIn() {
		assertFalse(managementSystem.adminLoggedIn());
	}
	
	@Given("admin id is {string}")
	public void theIdIs(String id) {
		this.id = id;
	}
	
	@Then("admin login succeeds")
	public void admingLogin() {
		assertTrue(managementSystem.adminLogin(id));
	}
	
	@Given("admin is logged in")
	public void adminLoggedIn() {
		assertTrue(managementSystem.adminLoggedIn());
	}
	
	@When("admin logs out")
	public void adminLogout() {
		managementSystem.adminLogout();
	}
	
	@Then("admin login fails")
	public void adminLoginFail() {
		assertFalse(managementSystem.adminLogin(id));
	}
	
	@Given("admin is already logged in")
	public void thatTheAdminIsLoggedIn() {
		assertTrue(managementSystem.adminLogin("admi"));
	}
	
	@Given("employee with ID {string} is logged in")
	public void employeeWithIDIsLoggedIn(String Id) throws OperationNotAllowedException{
	    createEmployee(Id);
	    assertTrue(managementSystem.employeeLogin(Id));
	}
	
	@Given("employee with ID {string} exists")
	public void employeeWithIDExists(String Id) throws OperationNotAllowedException{
	    createEmployee(Id);
	    assertNotNull(managementSystem.FindEmployeeById(Id));
	}
	
	@Given("admin is logged out")
	public void adminIsLoggedOut() {
		assertFalse(managementSystem.adminLogout());
	}
	
	@Given("employee {string} is logged out")
	public void employeeIsLoggedOut(String Id) {
	    assertFalse(managementSystem.employeeLogout());
	}
	
	@When("{string} logs in")
	public void logsIn(String employeeId) {
		try {
			managementSystem.employeeLogin(employeeId);
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}		
	}
	
	@Then("employee is logged in")
	public void isLoggedIn() {
		assertTrue(managementSystem.employeeLogged());
	}
	
	private void createEmployee(String Id) throws OperationNotAllowedException {
		managementSystem.adminLogin("admi");
		Employee employee = new Employee(Id);
		managementSystem.addEmployee(employee);
		managementSystem.adminLogout();
	}
}