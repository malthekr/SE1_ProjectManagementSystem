package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private String id;
    private String name;
    private List<Activity> activities = new ArrayList<>();;
    //private EMail Email;

    public Employee(String name, String id){
        this.name = name;
        this.id = id;
    }
    
    public Employee(String id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }
    
    public void addActivity(Activity activity) throws OperationNotAllowedException {
    	if(!activities.contains(activity)) {
    		activities.add(activity);
    		return;
    	}
    	
    	throw new OperationNotAllowedException("Employee is not part of the project");
    }

    public int getNumOfActivities(){
        return activities.size();
    }
    
    public List<Activity> getActivities(){
    	return activities;
    }
}