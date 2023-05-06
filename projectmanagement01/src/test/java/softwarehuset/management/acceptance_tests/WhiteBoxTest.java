package softwarehuset.management.acceptance_tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import softwarehuset.management.app.Employee;
import softwarehuset.management.app.Project;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;

import org.junit.jupiter.api.Test;

public class WhiteBoxTest {
	private ManagementSystemApp managementSystem = new ManagementSystemApp();
	private String errorMessage;
	private ProjectHelper projectHelper = new ProjectHelper();
	
	@Test
	public void testRemoveEmployeeInputDataSetA() throws OperationNotAllowedException {
		assertTrue(managementSystem.adminLogin("admi"));	// Admin needs to be logged in for employees to be added to system
		Employee e1 = new Employee("mkr");
		Employee e2 = new Employee("nik");
		managementSystem.addEmployee(e1);
		managementSystem.addEmployee(e2);
		managementSystem.adminLogout();						// Logout for admin
		assertFalse(managementSystem.adminLoggedIn());
		
		try {
			managementSystem.removeEmployee(e1);
	    } catch (OperationNotAllowedException e) {
	    	errorMessage = e.getMessage();
		}
		assertEquals("Administrator login required", errorMessage);
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
		
		managementSystem.removeEmployee(e1);
		
		assertFalse(managementSystem.containsEmployeeWithId(e1.getId()));
		assertFalse(p1.getEmployeesAssignedToProject().contains(e1));
	}
	
	@Test
	public void testRemoveEmployeeInputDataSetC() throws OperationNotAllowedException {
		assertTrue(managementSystem.adminLogin("admi"));
		
		Employee e1 = new Employee("mkr");
		Employee e2 = new Employee("hans");
		
		managementSystem.addEmployee(e1);
		managementSystem.addEmployee(e2);
		
		managementSystem.removeEmployee(e2);
		
		assertTrue(e1.getProjects().isEmpty());
		assertFalse(managementSystem.containsEmployeeWithId(e2.getId()));
	}
	
	@Test
	public void testRemoveEmployeeInputDataSetD() throws OperationNotAllowedException {
		assertTrue(managementSystem.adminLogin("admi"));
		
		Employee e1 = new Employee("nik");
		Employee e2 = new Employee("mkr");
		
		managementSystem.addEmployee(e1);
		
		managementSystem.removeEmployee(e2);
		
		assertFalse(managementSystem.containsEmployeeWithId(e2.getId()));
	}
	
}
	