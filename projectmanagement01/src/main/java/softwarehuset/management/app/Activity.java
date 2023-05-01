package softwarehuset.management.app;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;


public class Activity {
    private int projectId;
    private String description;
    private List<Employee> employees = new ArrayList<>();
    private double expectedHours;
    private Calendar startDate, endDate;
    private int unspecifiedEndDate = 14;
    
    public Activity(int projectId, String description, double expectedHours, Calendar startDate, Calendar endDate) {
    	this.projectId = projectId;
    	this.description = description;
    	this.expectedHours = expectedHours;
    	this.startDate = startDate;
    	this.endDate = endDate;
    }
    
    public Activity(int projectId, String description, Calendar startDate, Calendar endDate) {
    	this.projectId = projectId;
    	this.description = description;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.expectedHours = 0.0;
    }
    
    public void addEmployee(Employee employee) throws OperationNotAllowedException {
    	if (!employees.contains(employee)){
        	employees.add(employee);
        	return;
    	} 
    	throw new OperationNotAllowedException("Employee already part of activity");
    }
    
    public int getProjectId(){
        return projectId;
    }
    
    public List<Employee> getEmployees(){
        return employees;
    }
    
    public void setDescrption(String description) {
    	this.description = description;
    }
    
    public void setExpectedHours(double expectedHours) {
    	this.expectedHours = expectedHours;
    }
    
    public void setStartDate(Calendar startDate) {
    	this.startDate = startDate;
    }
    
    public void setEndDate(Calendar endDate) {
    	this.endDate = endDate;
    }
    
    public String getDescription() {
    	return description;
    }
    
}
