package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class Employee {
	private String id;
    private String name;
    // private List<Activity> activities = new ArrayList<>();;
    private static ConcurrentHashMap<Project, List<Activity>> map = new ConcurrentHashMap<>();
    private static List<Activity> activities;
	
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
//    	if(!activities.contains(activity)) {
//    		activities.add(activity);
//    		return;
//    	}
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
    
    public List<Activity> getActivities(){
    	return activities;
    }
}