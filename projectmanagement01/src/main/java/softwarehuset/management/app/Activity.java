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
    
    /*
    public Activity(int projectId, String description, double expectedHours, Calendar startDate, Calendar endDate) {
    	this.projectId = projectId;
    	this.description = description;
    	this.expectedHours = expectedHours;
    	this.startDate = startDate;
    	this.endDate = endDate;
    }
    */
    
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
    
    // Removes employee from activity
    public void removeEmployee(Employee employee) throws OperationNotAllowedException {  
    	if (!employees.contains(employee)){
 			throw new OperationNotAllowedException("Employee is not part of activity");      	
    	} 
    	employees.remove(employee);
    	// return;
    }
    
    public int getProjectId(){
        return projectId;
    }
    
    public void addWorkedHours(double hoursWorked){
        hoursWorked = hoursWorked - (hoursWorked % 0.5);
        this.workedHours += hoursWorked;
        
        if (this.workedHours < 0) {
        	this.workedHours = 0;
        }
    	
    	// return;
    	//throw new OperationNotAllowedException("Worked hours is measured in half hours worked");
    }
    
    public double getWorkedHours() {
    	return workedHours;
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
		this.expectedHours = 0 > expectedHours ? 0 : expectedHours - expectedHours % 0.5 ;
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
    
    public boolean match(String searchText) {
		if(this.getEmployees() != null) {
			boolean b = false;
			
			for(Employee a : getEmployees()) { 
				b = a.getId().equals(searchText) || a.getName().equals(searchText) ? true : b;
			}
			
			return String.valueOf(getProjectId()).contains(searchText) || description.contains(searchText) || getEmployees().contains(searchText) || b;
		} else {
			return description.contains(searchText);
		}
	}
	
	public String printDetail() {
		String name = this.getDescription().isBlank() ? "No description on activity" : this.getDescription();
		String id = this.getEmployees() == null ? "No employees assigned yet" : this.getEmployeesAsString();
		
		//Format start date as: Week/Year.
		int week = this.getStartDate().get(Calendar.WEEK_OF_YEAR);
		int year =  this.getStartDate().get(Calendar.YEAR);
		String start = week + "/" + year;
		//Format end date as: Week/Year
		int week1 = this.getEndDate().get(Calendar.WEEK_OF_YEAR);
		int year1 =  this.getEndDate().get(Calendar.YEAR);
		String end = week1 + "/" + year1;
		
		StringBuffer b = new StringBuffer();
		b.append("<html>"+"<br>");
		b.append(String.format("<b>Descriptions:</b>     %s<br>", name)); 
		b.append(String.format("<b>Employee:</b>     %s<br>", id));
		b.append(String.format("<b>Project Id:</b>    %s<br>", this.getProjectId()));
		b.append(String.format("<b>Start date (Week/Year):</b>     %s<br>", start));
		b.append(String.format("<b>End date (Week/Year):</b>       %s<br>", end));
		b.append(String.format("<b>Expected Hours:</b> %s<br>", this.getExpectedHours()));
		b.append(String.format("<b>Worked Hours:</b> %s<br></html>", this.getWorkedHours()));
		return b.toString();
	}
	
	public String getEmployeesAsString(){
        String name = "";
        int i = 0;
        
        for(Employee e : this.getEmployees()) {
            name += e.getId();
            if (i++ == this.getEmployees().size() - 1){
                break;
            }
            name += ", ";
        }
        return name;
    }
	
	public String toString() {
		String name = this.getDescription().isBlank() ? "" : this.getDescription();
		return "Project ID: " + String.valueOf(getProjectId()) + " - "+ name;
	}
    
}
