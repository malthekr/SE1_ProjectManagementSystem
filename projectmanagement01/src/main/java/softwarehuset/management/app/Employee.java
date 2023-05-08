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
	
    // Constructor to create an employee with only empoylee id
	public Employee(String id){
        this.id = id;
    }
	
	// Constructor to create an employee with name and empoylee id
    public Employee(String name, String id){
        this.name = name;
        this.id = id.toLowerCase();
    }

    // Get employee name
    public String getName(){
        return name;
    }
    
    // Get employee id
    public String getId(){
        return id;
    }
    
    // Get list of projects employee is working on
    public List<Project> getProjects(){
		List<Project> projects = new ArrayList<>();
		for (Project key : map.keySet()) {
		    projects.add(key);
		}
		return projects;
	}
    
    // Get list of activities employee is working on
    public List<Activity> getActivities(){
		List<Activity> a = new ArrayList<>();
		for(Project key : map.keySet()){
			a.addAll(map.get(key));
		}
    	return a;
    }
    
    // Get number of activities employee is working on
    public int getNumOfActivities(){
    	int numbOfActivites = map.values().stream().flatMapToInt(list -> IntStream.of(list.size())).sum();
    	
        return numbOfActivites;
    }
    
    // Get list of activities employee is working on in a specific project
    public List<Activity> listOfActivitiesInProject(Project project) {    	
    	List<Activity> employeeActivities = map.get(project);
    	return employeeActivities;   	
    }
    
    // Checks if employee is too busy to take on new activity
    public boolean isBusy(){
		return getNumOfActivities() > 20 ? true : false;
	}
    
    // Add activity to list of activities employee is working on
    // Throws exception - Employee has too many activities he is working on
    public void addActivity(Project project, Activity activity) throws OperationNotAllowedException {
		if(isBusy()) {
    		throw new OperationNotAllowedException("Employee too busy");
    	}
    	activities = map.computeIfAbsent(project, y -> new ArrayList<>());
    	
    	if(!activities.contains(activity)) {
    		activities.add(activity);
    		return;
    	}
    }
    
    // Add project/activity for employee (is not synced with project employee list or activity employee list)
    public void addProjectActivity(Project project, Activity activity) {
    	activities = map.computeIfAbsent(project, y -> new ArrayList<>());
    	
    	if(!activities.contains(activity)) {
    		activities.add(activity);
    		return;
    	}
	}
    
    // Remove an activity from list of activities employee is working on
    public void removeActivity(Project project, Activity activity) throws OperationNotAllowedException {
		List<Activity> a = map.get(project);
		a.remove(activity);
		activities = map.put(project, a);
    }
    
    //When searching with a key word these employees appear:
    public boolean match(String searchText) {
		return this.getName().contains(searchText) || this.getId().contains(searchText);
	}
    
    // Display on GUI what comes up when we search for a key word
    public String toString() {
		String name = this.getName().isBlank() ? "" : this.getName();
		return "Employee ID: " + this.getId() + " - "+ name;
	}
}