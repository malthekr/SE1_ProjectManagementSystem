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
    
    // [NIKLAS] Get project id activity is associated with
    public int getProjectId(){
        return projectId;
    }
    
    // [NIKLAS] Get description of activity
    public String getDescription() {
    	return description;
    }
    
    // [NIKLAS] Get worked hours on activity
    public double getWorkedHours() {
    	return workedHours;
    }
    
    // [NIKLAS] Get expected hours on activity
    public double getExpectedHours(){
        return expectedHours;
    }
    
    // [THOR] Get list of employees associated with this activity
    public List<Employee> getEmployees(){
        return employees;
    }
    
    // [THOR] Get start date of activity
    public Calendar getStartDate(){
        return startDate;
    }
    
    // [THOR] Get end date of activity
     public Calendar getEndDate(){
        return endDate;
    }
     
     // [THOR] Set the description of activity
     public void setDescrption(String description) {
     	this.description = description;
     }
     
     // [THOR] Set the expected hours activity is going to take
     public void setExpectedHours(double expectedHours) {
 		this.expectedHours = 0 > expectedHours ? 0 : expectedHours - expectedHours % 0.5 ;
     }
     
     // [THOR] Set start date of activity
     public void setStartDate(Calendar newStartDate) throws OperationNotAllowedException {
    	 if(newStartDate.compareTo(this.endDate) > 0) {
 			throw new OperationNotAllowedException("Start date is after end date");
 		}
 		this.startDate = newStartDate;
     }
     
     // [THOR] Set end date of activity
     public void setEndDate(Calendar newEndDate) throws OperationNotAllowedException {
    	 if(newEndDate.compareTo(this.startDate) < 0) {
 			throw new OperationNotAllowedException("End date is before start date");
 		}
     	this.endDate = newEndDate;
     }
     
     // [THOR] Add worked hours to activity
     public void addWorkedHours(double hoursWorked){
         hoursWorked = hoursWorked - (hoursWorked % 0.5);
         this.workedHours += hoursWorked;
         
         if (this.workedHours < 0) {
         	this.workedHours = 0;
         }
     }
    
    // [HANS] Adds employee to activity
    public void addEmployee(Employee employee) throws OperationNotAllowedException {  
    	if (employees.contains(employee)){
 			throw new OperationNotAllowedException("Employee already part of activity");      	
    	} 
    	
//    	if (employee.isBusy()){
//            throw new OperationNotAllowedException("Employee too busy");      	
//        }
    		employees.add(employee);
        	return;
    }
    
    // [HANS] Removes employee from activity
    public void removeEmployee(Employee employee) throws OperationNotAllowedException {  
    	if (!employees.contains(employee)){
 			throw new OperationNotAllowedException("Employee is not part of activity");      	
    	} 
    	employees.remove(employee);
    	// return;
    }
    
    // [NIKLAS] When searching with a key word these activities appear:
    public boolean match(String SearchText) {
        String searchText = SearchText.toLowerCase();
		if(this.getEmployees() != null) {
			boolean b = false;
			
			for(Employee a : getEmployees()) { 
                if(a.getName() != null){
					b = a.getId().contains(searchText) || a.getName().contains(searchText) ? true : b;
				} else {
                    b = a.getId().contains(searchText) ? true : b;
                }
			}
			
			return String.valueOf(getProjectId()).contains(searchText) || description.toLowerCase().contains(searchText) || b;
		} else {
			return description.toLowerCase().contains(searchText);
		}
	}
   
    // [NIKLAS] Display on GUI what comes up when we search for a key word
    public String toString() {
		String name = this.getDescription().isBlank() ? "" : this.getDescription();
		return "Project ID: " + String.valueOf(getProjectId()) + " - "+ name;
	}    
}
