package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;

import softwarehuset.management.app.Employee;
import softwarehuset.management.app.Project;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;

import org.junit.Test;

@RunWith(Cucumber.class)
public class WhiteBoxTest {
	private ManagementSystemApp managementSystem;
	private ErrorMessageHolder errorMessageHolder;
	private ProjectHelper projectHelper;

	public WhiteBoxTest(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder, ProjectHelper projectHelper) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.projectHelper = projectHelper;
	}
	
	@Test
	public void testRemoveEmployeeInputDataSetA() throws OperationNotAllowedException {
		assertFalse(managementSystem.adminLogin(""));
		Employee e1 = new Employee("mkr");
		Employee e2 = new Employee("nik");
		managementSystem.addEmployee(e1);
		managementSystem.addEmployee(e2);
		
		try {
			managementSystem.removeEmployee(e1);
	    } catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
		assertEquals("Administrator login required", errorMessageHolder);
	}
	
	@Test
	public void testRemoveEmployeeInputDataSetB() throws OperationNotAllowedException {
		assertTrue(managementSystem.adminLogin("admi"));
		Employee e1 = new Employee("mkr");
		Employee e2 = new Employee("nik");
		Project p1 = projectHelper.getProject("project1");
		Project p2 = projectHelper.getProject("project2");
		
		managementSystem.addEmployee(e1);
		managementSystem.addEmployee(e2);
		managementSystem.createProject(p1);
		managementSystem.createProject(p2);
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
		managementSystem.addEmployeeToProject(p1.getProjectID(), e2.getId());
		
		
	}
	
	public void testRemoveEmployeeInputDataSetC() throws OperationNotAllowedException {
		
	}
	
	public void testRemoveEmployeeInputDataSetD() throws OperationNotAllowedException {
		
	}
	
}
	