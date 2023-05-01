package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import softwarehuset.management.app.Activity;
import softwarehuset.management.app.DateServer;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;

public class ProjectManagerSteps {
	
	private ManagementSystemApp managementSystem;
	private ErrorMessageHolder errorMessageHolder;
	private Project project;
	private EmployeeHelper employeeHelper;
	private List<Activity> activities;
	
	public ProjectManagerSteps(ManagementSystemApp managementSystem, ErrorMessageHolder errorMessageHolder, EmployeeHelper employeeHelper, Project project) {
		this.managementSystem = managementSystem;
		this.errorMessageHolder = errorMessageHolder;
		this.employeeHelper = employeeHelper;
		this.project = project;
	}
	


}
