package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import softwarehuset.management.app.ManagementSystemApp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginLogoutSteps {

	private ManagementSystemApp managementSystem;
	private String id;
	
	public LoginLogoutSteps(ManagementSystemApp managementSystem) {
		this.managementSystem = managementSystem;
	}
	
	@Given("admin is not logged in")
	public void thatTheAdministratorIsNotLoggedIn() throws Exception {
		assertFalse(managementSystem.adminLoggedIn());
	}
	
	@Given("admin id is {string}")
	public void theIdIs(String id) throws Exception {
		this.id = id;
	}
	
	@Then("admin login succeeds")
	public void admingLogin() throws Exception {
		assertTrue(managementSystem.adminLogin(id));
	}
	
	@Given("admin is logged in")
	public void adminLoggedIn() throws Exception {
		assertTrue(managementSystem.adminLoggedIn());
	}
	
	@When("admin logs out")
	public void adminLogout() throws Exception {
		managementSystem.adminLogout();
	}
	
	@Then("admin login fails")
	public void adminLoginFail() throws Exception {
		assertFalse(managementSystem.adminLogin(id));
	}
	
	@Given("admin is already logged in")
	public void thatTheAdminIsLoggedIn() throws Exception {
		assertTrue(managementSystem.adminLogin("admi"));
	}
}