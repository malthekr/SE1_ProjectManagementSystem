package softwarehuset.management.acceptance_tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


import softwarehuset.management.app.Employee;
import softwarehuset.management.app.Project;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;

import org.junit.jupiter.api.Test;

public class WhiteBoxTest {
	private ManagementSystemApp managementSystem = new ManagementSystemApp();
	private String errorMessage;
	private ProjectHelper projectHelper = new ProjectHelper();
	
	
	// Remove Employee
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
	
	// Claim/unclaim project manager status
	@Test
	public void testTogglePMClaimInputDataSetA() throws OperationNotAllowedException {
		// Input Data
		assertTrue(managementSystem.adminLogin("admi"));
		Employee e1 = new Employee("mkr");
		managementSystem.addEmployee(e1);
		
		Project p1 = projectHelper.getProject("pr1");
		managementSystem.createProject(p1);
		
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
		
		managementSystem.promoteToPm(p1.getProjectID(), e1.getId());
		managementSystem.adminLogout();
		
		String input = "mkr";
	
		managementSystem.employeeLogin(e1.getId());
		
		// Expected Result
		assertFalse(managementSystem.togglePMClaim(p1, input));
		assertNull(p1.getProjectManager());
	}
	
	@Test
	public void testTogglePMClaimInputDataSetB() throws OperationNotAllowedException {
		// Input Data
		assertTrue(managementSystem.adminLogin("admi"));
		Employee e1 = new Employee("mkr");
		managementSystem.addEmployee(e1);
		
		Project p1 = projectHelper.getProject("pr1");
		managementSystem.createProject(p1);
		
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
		managementSystem.adminLogout();
		
		String input = "mkr";
	
		managementSystem.employeeLogin(e1.getId());
		
		// Expected Result
		assertNull(p1.getProjectManager());
		assertTrue(managementSystem.togglePMClaim(p1, input));
		assertTrue(p1.getProjectManager().equals(e1));
	}
	
	@Test
	public void testTogglePMClaimInputDataSetC() throws OperationNotAllowedException {
		// Input Data
		assertTrue(managementSystem.adminLogin("admi"));
		Employee e1 = new Employee("mkr");
		managementSystem.addEmployee(e1);
		Employee e2 = new Employee("thr");
		managementSystem.addEmployee(e2);
		
		Project p1 = projectHelper.getProject("pr1");
		managementSystem.createProject(p1);
		
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
		managementSystem.addEmployeeToProject(p1.getProjectID(), e2.getId());
		
		managementSystem.promoteToPm(p1.getProjectID(), e2.getId());		// "thr" is project manager
		managementSystem.adminLogout();
		
		String input = "mkr";
	
		managementSystem.employeeLogin(e1.getId());
		
		// Expected Result
		try {
			managementSystem.togglePMClaim(p1, input);
		} catch (OperationNotAllowedException e) {
	    	errorMessage = e.getMessage();
		}
		assertEquals("Project already has PM", errorMessage);
		
	}
	
	@Test
	public void testTogglePMClaimInputDataSetD() throws OperationNotAllowedException {
		// Input Data
		assertTrue(managementSystem.adminLogin("admi"));
		Employee e1 = new Employee("mkr");
		managementSystem.addEmployee(e1);
		Employee e2 = new Employee("thr");
		managementSystem.addEmployee(e2);
		
		Project p1 = projectHelper.getProject("pr1");
		managementSystem.createProject(p1);
		
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
		managementSystem.addEmployeeToProject(p1.getProjectID(), e2.getId());
		managementSystem.adminLogout();
		
		String input = "mkr";
		
		managementSystem.employeeLogin(e2.getId());
		
		// Expected Result
		assertTrue(managementSystem.togglePMClaim(p1, input));
		assertTrue(p1.getProjectManager().equals(e1));
	}
	
	// Add employee to project
	@Test
	public void testAddEmployeeToProjectInputDataSetA() throws OperationNotAllowedException {
		// Input Data
		managementSystem.adminLogin("admi");
		Employee e1 = new Employee("thr");
		managementSystem.addEmployee(e1);
		Employee e2 = new Employee("nik");
		managementSystem.addEmployee(e2);
		
		Project p1 = projectHelper.getProject("proj1");
		managementSystem.createProject(p1);
		
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
				
		managementSystem.adminLogout();
		managementSystem.employeeLogin(e1.getId());
		managementSystem.togglePMClaim(p1, e1.getId());
		
		// Expected Result
		assertTrue(p1.getProjectManager().equals(e1));
		
		assertFalse(p1.getEmployeesAssignedToProject().contains(e2));
		managementSystem.addEmployeeToProject(p1.getProjectID(), e2.getId());
		assertTrue(p1.getEmployeesAssignedToProject().contains(e2));
	}
	
	@Test
	public void testAddEmployeeToProjectInputDataSetB() throws OperationNotAllowedException {
		// Input Data
		managementSystem.adminLogin("admi");
		Employee e1 = new Employee("thr");
		managementSystem.addEmployee(e1);
		Employee e2 = new Employee("nik");
		managementSystem.addEmployee(e2);
		
		Project p1 = projectHelper.getProject("proj1");
		managementSystem.createProject(p1);
		
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
				
		managementSystem.adminLogout();
		managementSystem.employeeLogin(e2.getId());
		
		// Expected Result
		assertFalse(p1.hasProjectManager());
		assertFalse(p1.getEmployeesAssignedToProject().contains(e2));
		managementSystem.addEmployeeToProject(p1.getProjectID(), e2.getId());
		assertTrue(p1.getEmployeesAssignedToProject().contains(e2));
	}
	
	@Test
	public void testAddEmployeeToProjectInputDataSetC() throws OperationNotAllowedException {
		// Input Data
		managementSystem.adminLogin("admi");
		
		Employee e1 = new Employee("nik");
		managementSystem.addEmployee(e1);
		
		Project p1 = projectHelper.getProject("proj1");
		managementSystem.createProject(p1);
		
		// Expected Result
		assertTrue(managementSystem.adminLoggedIn());
		assertFalse(p1.hasProjectManager());
		assertFalse(p1.getEmployeesAssignedToProject().contains(e1));
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
		assertTrue(p1.getEmployeesAssignedToProject().contains(e1));
	}
	
	@Test
	public void testAddEmployeeToProjectInputDataSetD() throws OperationNotAllowedException {
		// Input Data
		managementSystem.adminLogin("admi");
		
		Employee e1 = new Employee("thr");
		managementSystem.addEmployee(e1);
		Employee e2 = new Employee("nik");
		managementSystem.addEmployee(e2);
		
		Project p1 = projectHelper.getProject("proj1");
		managementSystem.createProject(p1);
		
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
		p1.setProjectManager(e1);
		managementSystem.adminLogout();
		
		managementSystem.employeeLogin(e2.getId());
		
		// Expected Result
		assertFalse(p1.getProjectManager().equals(e2));
		assertFalse(p1.getEmployeesAssignedToProject().contains(e2));
		try {
			managementSystem.addEmployeeToProject(p1.getProjectID(), e2.getId());
		} catch (OperationNotAllowedException e) {
	    	errorMessage = e.getMessage();
		}
		assertEquals("Project Manager login required", errorMessage);
	}
	
	// CheckAuth
	@Test
	public void testCheckAuthInputDataSetA() throws OperationNotAllowedException {
		// Input Data
		managementSystem.adminLogin("admi");
		
		Employee e1 = new Employee("thr");
		managementSystem.addEmployee(e1);
		Employee e2 = new Employee("nik");
		managementSystem.addEmployee(e2);
		
		Project p1 = projectHelper.getProject("proj1");
		managementSystem.createProject(p1);
		
		managementSystem.adminLogout();
		
		managementSystem.employeeLogin(e2.getId());
		
		// Expected Result
		try {
			managementSystem.checkAuth(p1);
		} catch (OperationNotAllowedException e) {
	    	errorMessage = e.getMessage();
		}
		assertEquals("Project Manager login required", errorMessage);
	}
	
	@Test
	public void testCheckAuthInputDataSetB() throws OperationNotAllowedException {
		// Input Data
		managementSystem.adminLogin("admi");
		
		Employee e1 = new Employee("nik");
		managementSystem.addEmployee(e1);
		
		Project p1 = projectHelper.getProject("proj1");
		managementSystem.createProject(p1);
		p1.setProjectManager(e1);
		managementSystem.adminLogout();
		
		managementSystem.employeeLogin(e1.getId());
		
		// Expected Result
		assertTrue(managementSystem.checkAuth(p1));
	}
	
	@Test
	public void testCheckAuthInputDataSetC() throws OperationNotAllowedException {
		// Input Data
		managementSystem.adminLogin("admi");
		
		Employee e1 = new Employee("nik");
		managementSystem.addEmployee(e1);
		
		Project p1 = projectHelper.getProject("proj1");
		managementSystem.createProject(p1);
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
		
		managementSystem.adminLogout();
		
		managementSystem.employeeLogin(e1.getId());
		
		// Expected Result
		assertFalse(p1.hasProjectManager());
		assertTrue(managementSystem.checkAuth(p1));
	}
	
	@Test
	public void testCheckAuthInputDataSetD() throws OperationNotAllowedException {
		// Input Data
		managementSystem.adminLogin("admi");
		
		Employee e1 = new Employee("nik");
		managementSystem.addEmployee(e1);
		
		Project p1 = projectHelper.getProject("proj1");
		managementSystem.createProject(p1);
		
		// Expected Result
		assertTrue(managementSystem.adminLoggedIn());
		assertTrue(managementSystem.checkAuth(p1));
		
	}
	
	@Test
	public void testCheckAuthInputDataSetE() throws OperationNotAllowedException {
		// Input Data
		managementSystem.adminLogin("admi");
		
		Employee e1 = new Employee("thr");
		managementSystem.addEmployee(e1);
		Employee e2 = new Employee("nik");
		managementSystem.addEmployee(e2);
		
		Project p1 = projectHelper.getProject("proj1");
		managementSystem.createProject(p1);
		managementSystem.addEmployeeToProject(p1.getProjectID(), e1.getId());
		p1.setProjectManager(e1);
		
		managementSystem.adminLogout();
		
		managementSystem.employeeLogin(e2.getId());
		
		// Expected Result
		try {
			managementSystem.checkAuth(p1);
		} catch (OperationNotAllowedException e) {
	    	errorMessage = e.getMessage();
		}
		assertEquals("Project Manager login required", errorMessage);
	}
	
	
}
	