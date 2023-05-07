package softwarehuset.management.app;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Activity {
    private int projectId;
    private String description;
    private List<Employee> employees = new ArrayList<>();
    private double expectedHours;
    private double workedHours = 0;
    private Calendar startDate, endDate;
    
    // Constructor to create an activity
    public Activity(int projectId, String description, Calendar startDate, Calendar endDate) {
    	this.projectId = projectId;
    	this.description = description;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.expectedHours = 0.0;
    }
    
    // Get project id activity is associated with
    public int getProjectId(){
        return projectId;
    }
    
    // Get description of activity
    public String getDescription() {
    	return description;
    }
    
    // Get worked hours on activity
    public double getWorkedHours() {
    	return workedHours;
    }
    
    // Get expected hours on activity
    public double getExpectedHours(){
        return expectedHours;
    }
    
    // Get list of employees associated with this activity
    public List<Employee> getEmployees(){
        return employees;
    }
    
    // Get start date of activity
    public Calendar getStartDate(){
        return startDate;
    }
    
    // Get end date of activity
     public Calendar getEndDate(){
        return endDate;
    }
     
     // Set the description of activity
     public void setDescrption(String description) {
     	this.description = description;
     }
     
     // Set the expected hours activity is going to take
     public void setExpectedHours(double expectedHours) {
 		this.expectedHours = 0 > expectedHours ? 0 : expectedHours - expectedHours % 0.5 ;
     }
     
     // Set start date of activity
     public void setStartDate(Calendar startDate) {
     	this.startDate = startDate;
     }
     
     // Set end date of activity
     public void setEndDate(Calendar endDate) {
     	this.endDate = endDate;
     }
     
     // Add worked hours to activity
     public void addWorkedHours(double hoursWorked){
         hoursWorked = hoursWorked - (hoursWorked % 0.5);
         this.workedHours += hoursWorked;
         
         if (this.workedHours < 0) {
         	this.workedHours = 0;
         }
     }
    
    // Adds employee to activity
    public void addEmployee(Employee employee) throws OperationNotAllowedException {  
    	if (employees.contains(employee)){
 			throw new OperationNotAllowedException("Employee already part of activity");      	
    	} 
    	
    	if (employee.isBusy()){
            throw new OperationNotAllowedException("Employee too busy");      	
        }
    		employees.add(employee);
        	return;
    }
    
    // Removes employee from activity
    public void removeEmployee(Employee employee) throws OperationNotAllowedException {  
    	if (!employees.contains(employee)){
 			throw new OperationNotAllowedException("Employee is not part of activity");      	
    	} 
    	employees.remove(employee);
    	// return;
    }
    
    // When searching with a key word these activities appear:
    public boolean match(String searchText) {
		if(this.getEmployees() != null) {
			boolean b = false;
			
			for(Employee a : getEmployees()) { 
                if(a.getName() != null){
					b = a.getId().contains(searchText) || a.getName().contains(searchText) ? true : b;
				} else {
                    b = a.getId().contains(searchText) ? true : b;
                }
			}
			
			return String.valueOf(getProjectId()).contains(searchText) || description.contains(searchText) || b;
		} else {
			return description.contains(searchText);
		}
	}
   
    // Display on GUI what comes up when we search for a key word
    public String toString() {
		String name = this.getDescription().isBlank() ? "" : this.getDescription();
		return "Project ID: " + String.valueOf(getProjectId()) + " - "+ name;
	}    
}
