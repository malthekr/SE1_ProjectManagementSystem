package softwarehuset.management.acceptance_tests;

import softwarehuset.management.app.Project;
import softwarehuset.management.app.DateServer;
import softwarehuset.management.app.ManagementSystemApp;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ProjectHelper {
	private Project project;
	private MockDateHolder dateHolder; 
	private Calendar startDate;
	private Calendar endDate;
	private Double workHours;
	private ManagementSystemApp managementSystem;
	
	public Project getProject() {
		if (project == null) {
			project = exampleProject();
		}
		return project;
	}
	
	private Project exampleProject() {
		// Example project creates a project with start date today and end date next week.
		// Work hours = 20
		Calendar calendar = new GregorianCalendar();
		startDate = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		endDate = new GregorianCalendar();
		endDate.setTime(startDate.getTime());
		endDate.add(Calendar.DAY_OF_YEAR, 7);		
		workHours = 20.0;
		
//		System.out.println(startDate);
//		System.out.println(endDate);
		
		int year = startDate.get(Calendar.YEAR) % 100;
		int id = managementSystem.generateID(year);
		Project project= new Project("Example project", workHours, startDate, endDate, id);
		return project;
	}

	public void setUser(Project projectInfo) {
		project = projectInfo;
	}
}