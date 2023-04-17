package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import softwarehuset.management.app.ManagementSystem;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginLogoutSteps {

	private ManagementSystem managementSystem;
	private String id;
	
	public LoginLogoutSteps(ManagementSystem managementSystem) {
		this.managementSystem = managementSystem;
	}
	
	@Given("that the administrator is not logged in")
	public void thatTheAdministratorIsNotLoggedIn() throws Exception {
		assertFalse(managementSystem.adminLoggedIn());
	}
	
	@Given("the admin id is {string}")
	public void theIdIs(String id) throws Exception {
		this.id = id;
	}
	
	@Then("the administrator login succeeds")
	public void admingLogin() throws Exception {
		assertTrue(managementSystem.adminLogin(id));
	}
	
	@Given("the adminstrator is logged in")
	public void adminLoggedIn() throws Exception {
		assertTrue(managementSystem.adminLoggedIn());
	}
	
	@When("the administrator logs out")
	public void adminLogout() throws Exception {
		managementSystem.adminLogout();
	}
	
	@Then("the administrator is not logged in")
	public void adminNotLoggedIn() throws Exception {
		assertFalse(managementSystem.adminLoggedIn());
	}
	
	@Then("the administrator login fails")
	public void adminLoginFail() throws Exception {
		assertFalse(managementSystem.adminLogin(id));
	}
}