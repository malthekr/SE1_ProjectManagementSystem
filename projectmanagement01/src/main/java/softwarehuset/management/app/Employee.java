package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class Employee {
	private String id;
    private String name;
    // private List<Activity> activities = new ArrayList<>();;
    private ConcurrentHashMap<Project, List<Activity>> map = new ConcurrentHashMap<>();
    private List<Activity> activities;
    //private static ConcurrentHashMap<Project, List<Activity>> map = new ConcurrentHashMap<>();
    //private static List<Activity> activities;
	//Slettede static fra activities og map pga at vi gemmer individuel list for hver employee iforhold til at genere ID.
	
	public Employee(String id){
        this.id = id;
    }
	
    public Employee(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
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

    public int getNumOfActivities(){
    	int numbOfActivites = map.values().stream().flatMapToInt(list -> IntStream.of(list.size())).sum();
    	
        return numbOfActivites;
    	//return activities.size();
    }
    
    public boolean isBusy(){
		return getNumOfActivities() > 20 ? true : false;
	}
    
    public boolean isPartOfActivity(Project project, Activity activity) {    	
    	List<Activity> employeeActivities = map.get(project);
    	return employeeActivities.contains(activity);    	
    }
    
    public List<Activity> getActivities(){
    	return activities;
    }
}