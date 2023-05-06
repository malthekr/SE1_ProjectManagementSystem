package softwarehuset.management.acceptance_tests;

import softwarehuset.management.app.Project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ProjectHelper {
	private Project project;
	private Calendar startDate;
	private Calendar endDate;
	private Double workHours;
	
	public Project getProject() {
		if (project == null) {
			project = exampleProject();
		} 
//		project = exampleProject();
		return project;
	}
	
	public Project getProject(String name) {
		project = exampleProject();
		project.editProjectName(name);
		return project;
	}
	
	private Project exampleProject() {
		// Example project creates a project with start date today and end date next week.
		// Work hours = 20
		Calendar calendar = new GregorianCalendar();
		Calendar startDate = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		Calendar endDate = new GregorianCalendar();
		endDate.setTime(startDate.getTime());
		endDate.add(Calendar.DAY_OF_YEAR, 7);		
		Double workHours = 20.0;
		
		Project project= new Project("Example project", workHours, startDate, endDate);
		return project;
	}
/*
	public void setUser(Project projectInfo) {
		project = projectInfo;
	}
*/
}