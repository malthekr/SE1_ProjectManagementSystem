package softwarehuset.management.app;

public class Employee {
	private String id;
    private String name;
    private int numOfActivities;
    //private EMail Email;

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

    public int getNumOfActivities(){
        return numOfActivities;
    }
}