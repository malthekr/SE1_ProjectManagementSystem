package softwarehuset.management.app;

import java.util.Calendar;

public class Activity {
    private int projectId;
    private String description;
    private Employee employee;
    private double expectedHours;
    private Calendar startDate, endDate;
    private int unspecifiedEndDate = 14;
    
    public Activity(int projectId, Employee employee, String description, double expectedHours, Calendar startDate, Calendar endDate) {
    	this.projectId = projectId;
    	this.employee = employee;
    	this.description = description;
    	this.expectedHours = expectedHours;
    	this.startDate = startDate;
    	this.endDate = endDate;
    }
    
    public Activity(int projectId, Employee employee, String description, Calendar startDate, Calendar endDate) {
    	this.employee = employee;
    	this.projectId = projectId;
    	this.description = description;
    	this.startDate = startDate;
    	this.endDate = endDate;
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
