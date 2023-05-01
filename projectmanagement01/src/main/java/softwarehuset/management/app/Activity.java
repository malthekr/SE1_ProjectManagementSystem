package softwarehuset.management.app;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private int projectId;
    private String description;
    private List<Employee> employees = new ArrayList<>();
    private double expectedHours;
    private double workedHours = 0;
    private Calendar startDate, endDate;
    
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
    
    public int getProjectId(){
        return projectId;
    }
    
    public void addWorkedHours(double hoursWorked) throws OperationNotAllowedException {
        hoursWorked = hoursWorked - (hoursWorked % 0.5);
        this.workedHours += hoursWorked;
        
        if (this.workedHours < 0) {
        	this.workedHours = 0;
        }
    	
    	return;
    	//throw new OperationNotAllowedException("Worked hours is measured in half hours worked");
    }
    
    public double getWorkedHours() {
    	return this.workedHours;
    }
    
    public List<Employee> getEmployees(){
        return employees;
    }
    
    public Calendar getStartDate(){
        return startDate;
    }
    
     public Calendar getEndDate(){
        return endDate;
    }
    
    public double getExpectedHours(){
        return expectedHours;
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
