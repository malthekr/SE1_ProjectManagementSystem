package softwarehuset.management.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.Activity;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProjectManagerSteps {
	private ManagementSystemApp managementSystemApp;
	private Activity activity;
	private Employee employee;
	private ErrorMessageHolder errorMessageHolder;
	private Project project;

	private Calendar startDate;
	private Calendar endDate;

	public ProjectManagerSteps(ManagementSystemApp managementSystemApp, Project project, Activity activity,
			Employee employee, ErrorMessageHolder errorMessageHolder) {
		this.managementSystemApp = managementSystemApp;
		this.errorMessageHolder = errorMessageHolder;
		this.project = project;
		this.employee = employee;
		this.activity = activity;
	}

}
