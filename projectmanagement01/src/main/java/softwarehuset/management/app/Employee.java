package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class Employee {
	private String id;
    private String name;
    private ConcurrentHashMap<Project, List<Activity>> map = new ConcurrentHashMap<>();
    private List<Activity> activities;
	
	public Employee(String id){
        this.id = id;
    }
	
    public Employee(String name, String id){
        this.name = name;
        this.id = id.toLowerCase();
    }

    public String getName(){
        return name;
    }
    
    public String getId(){
        return id;
    }
    
    public List<Project> getProjects(){
		List<Project> projects = new ArrayList<>();
		for (Project key : map.keySet()) {
		    projects.add(key);
		}
		return projects;
	}
    
    public void addActivity(Project project, Activity activity) throws OperationNotAllowedException {
		if(isBusy()) {
    		throw new OperationNotAllowedException("Employee too busy");
    	}
    	activities = map.computeIfAbsent(project, y -> new ArrayList<>());
    	
    	if(!activities.contains(activity)) {
    		activities.add(activity);
    		return;
    	}
    	
    	throw new OperationNotAllowedException("Employee is not part of the project");
    }
    
    // Add project/activity for employee (is not synced with project employee list or activity employee list)
    public void addProjectActivity(Project project, Activity activity) {
    	activities = map.computeIfAbsent(project, y -> new ArrayList<>());
    	
    	if(!activities.contains(activity)) {
    		activities.add(activity);
    		return;
    	}
	}
    
    public void removeActivity(Project project, Activity activity) throws OperationNotAllowedException {
		List<Activity> a = map.get(project);
		a.remove(activity);
		activities = map.put(project, a);
		
		
		
    	/*activities = map.computeIfAbsent(project, y -> new ArrayList<>());
    	
    	if(activities.contains(activity)) {
    		activities.remove(activity);
    		return;
    	}*/
    	//map.get(project).remove(activity);
    	
    	//throw new OperationNotAllowedException("Employee is not part of this activity");
    }

    public int getNumOfActivities(){
    	int numbOfActivites = map.values().stream().flatMapToInt(list -> IntStream.of(list.size())).sum();
    	
        return numbOfActivites;
    }
    
    public boolean isBusy(){
		return getNumOfActivities() > 20 ? true : false;
	}
  
    public List<Activity> listOfActivitiesInProject(Project project) {    	
    	List<Activity> employeeActivities = map.get(project);
    	return employeeActivities;   	
    }
     
    public List<Activity> getActivities(){
		List<Activity> a = new ArrayList<>();
		for(Project key : map.keySet()){
			a.addAll(map.get(key));
		}
    	return a;
    }
    
    public boolean match(String searchText) {
		return this.getName().contains(searchText) || this.getId().contains(searchText);
	}
    
    public String getStatusOfEmployee(boolean showActiveProjects){
		List<Project> projects = this.getProjects();
		
		StringBuffer b = new StringBuffer();
		b.append("<html>");
		
		boolean containsActivities = true;
		
		for(Project p : projects){
			if(p.getOngoingProject() || !showActiveProjects){
				b.append(String.format("<b>Project:</b>     %s<br>", p.getProjectName())); 
				List<TimeTable> timeTables = p.getTimeTablesByEmployee(this);
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
			b.append("<br><b>No active projects or activities assigned to this employee</b>");
		}
		
		b.append("</html>");
		return b.toString();
	}
    
    public String toString() {
		String name = this.getName().isBlank() ? "" : this.getName();
		return "Employee ID: " + this.getId() + " - "+ name;
	}
}