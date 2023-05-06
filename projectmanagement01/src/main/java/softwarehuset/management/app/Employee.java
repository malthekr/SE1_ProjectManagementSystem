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
    
    //When searching with a key word these employees appear:
    public boolean match(String searchText) {
		return this.getName().contains(searchText) || this.getId().contains(searchText);
	}
    
    //To display on GUI what comes up when we search for a key word
    public String toString() {
		String name = this.getName().isBlank() ? "" : this.getName();
		return "Employee ID: " + this.getId() + " - "+ name;
	}
}