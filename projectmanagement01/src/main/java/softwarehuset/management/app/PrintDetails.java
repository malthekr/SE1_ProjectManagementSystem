package softwarehuset.management.app;

import java.util.Calendar;
import java.util.List;

public class PrintDetails {
	
	public PrintDetails(){
		
	}
	
	// Print project details on GUI
	public String projectDetails(Project project) {
		String name = project.getProjectName().isBlank() ? "No project name yet" : project.getProjectName();
		String pmid = !project.hasProjectManager() ? "No PM assigned yet" : project.getProjectManager().getId();
		String id = project.getEmployeesAssignedToProject() == null ? "No employees assigned yet" : getProjectEmployeesAsString(project);
		//Format start date as: Week/Year.
		int week = project.getStartDate().get(Calendar.WEEK_OF_YEAR);
		int year =  project.getStartDate().get(Calendar.YEAR);
		String start = week + "/" + year;
		//Format end date as: Week/Year
		int week1 = project.getEndDate().get(Calendar.WEEK_OF_YEAR);
		int year1 =  project.getEndDate().get(Calendar.YEAR);
		String end = week1 + "/" + year1;
		
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		b.append(String.format("<b>Name:</b>     %s<br>", name));
		b.append(String.format("<b>Project Id:</b>    %s<br>", project.getProjectID()));
		b.append(String.format("<b>Project Manager:</b>     %s<br>", pmid));
		b.append(String.format("<b>Employees:</b>     %s<br>", id));
		b.append(String.format("<b>Start date (Week/Year):</b>     %s<br>", start));
		b.append(String.format("<b>End date (Week/Year):</b>       %s<br>", end));
		b.append(String.format("<b>Is project Active?:</b> %s<br>", project.getOngoingProject()));
		b.append(String.format("<b>Expected Hours:</b> %s<br>", project.getExpectedHours()));
		b.append(String.format("<b>Worked Hours:</b>     %s<br>", project.getWorkedHours()));
		b.append("</html>");
			return b.toString();
		}
	
	//Print activity details on GUI
	public String activityDetail(Activity activity) {
		String name = activity.getDescription().isBlank() ? "No description on activity" : activity.getDescription();
		String id = activity.getEmployees() == null ? "No employees assigned yet" : getActivityEmployeesAsString(activity);
		
		//Format start date as: Week/Year.
		int week = activity.getStartDate().get(Calendar.WEEK_OF_YEAR);
		int year =  activity.getStartDate().get(Calendar.YEAR);
		String start = week + "/" + year;
		//Format end date as: Week/Year
		int week1 = activity.getEndDate().get(Calendar.WEEK_OF_YEAR);
		int year1 =  activity.getEndDate().get(Calendar.YEAR);
		String end = week1 + "/" + year1;
		
		StringBuffer b = new StringBuffer();
		b.append("<html>"+"<br>");
		b.append(String.format("<b>Descriptions:</b>     %s<br>", name)); 
		b.append(String.format("<b>Employee:</b>     %s<br>", id));
		b.append(String.format("<b>Project Id:</b>    %s<br>", activity.getProjectId()));
		b.append(String.format("<b>Start date (Week/Year):</b>     %s<br>", start));
		b.append(String.format("<b>End date (Week/Year):</b>       %s<br>", end));
		b.append(String.format("<b>Expected Hours:</b> %s<br>", activity.getExpectedHours()));
		b.append(String.format("<b>Worked Hours:</b> %s<br></html>", activity.getWorkedHours()));
		return b.toString(); 
	}
	
	// Print status of employee on GUI
	public String getStatusOfEmployee(Employee employee, boolean showActiveProjects){
		List<Project> projects = employee.getProjects();
		
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		
		boolean containsActivities = true;
		
		for(Project p : projects){
			if(p.getOngoingProject() || !showActiveProjects){
				b.append(String.format("<b>Project:</b>     %s<br>", p.getProjectName())); 
				List<TimeTable> timeTables = p.getTimeTablesByEmployee(employee);
				for(TimeTable t : timeTables){					
					//Format start date as: day/month/Year.
					int day = t.getDate().get(Calendar.DAY_OF_MONTH);
					int month = t.getDate().get(Calendar.MONTH) + 1;
					int year =  t.getDate().get(Calendar.YEAR);
					String datee = day + "/" + month + "/" + year;
					
					String s = t.getActivity().getDescription() + " - " 
						+ t.getHoursWorked() + " - "
						+ t.getActivity().getWorkedHours() + " of " + t.getActivity().getExpectedHours() 
						+ " - " + datee;
						b.append(String.format("<b> - </b>     %s<br>", s));
				}
				if(p.getOngoingProject()) {
					containsActivities = false;
				}
			}
		}
		
		if(containsActivities){
			b.append("<br><b>No active projects/activities assigned to employee</b>");
		}
		
		b.append("</html>");
		return b.toString();
	}
	
	// Print status report of project on GUI
	public String getStatusReport(Project project) {		
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		for(Activity a : project.getActivites()) {
			List<Employee> employeesWithHoursInActivity = project.getEmployeesFromTimeTable(a);
			
			String activityInfo = a.getDescription() + "(" + a.getWorkedHours() + "h ~ " + a.getExpectedHours() + "h)";
			b.append(String.format("<b>%s</b><br>", activityInfo)); 
			
			for(Employee e : employeesWithHoursInActivity) {
				// If employee added hours to project he is not part of
				if(e.listOfActivitiesInProject(project) != null) {
					if(e.listOfActivitiesInProject(project).contains(a)) {
						String employeeInfo = " - " + e.getId();
						
						
						if(!a.getEmployees().contains(e)) {
							employeeInfo += " (not part of activity)";
						}
						
						
						double workedHoursSum = 0;
						for(TimeTable t : project.getTimeTableForEmployeeAndActivity(e, a, project.getTimeTables())) {
							workedHoursSum += t.getHoursWorked();
						}
						
						String workedHours = ": " + workedHoursSum + "h";
						
						String employeeWorkedHours = employeeInfo + workedHours;
						b.append(String.format("%s <br>", employeeWorkedHours)); 
						
					}
				}
			}
		}
		
		b.append("</html>");
		return b.toString();
	}
	
	
	// Print employees working on activity
	private String getActivityEmployeesAsString(Activity activity){
        String name = "";
        int i = 0;
        
        for(Employee e : activity.getEmployees()) {
            name += e.getId();
            if (i++ == activity.getEmployees().size() - 1){
                break;
            }
            name += ", ";
        }
        return name;
    }
	
	
	
	// Print employees working on project
	private String getProjectEmployeesAsString(Project project){
	    String name = "";
	    int i = 0;
	        
	    for(Employee e : project.getEmployeesAssignedToProject()) {
	        name += e.getId();
	        if (i++ == project.getEmployeesAssignedToProject().size() - 1){ break;} 
	        name += ", ";
	        }
	        return name;
	    }
}