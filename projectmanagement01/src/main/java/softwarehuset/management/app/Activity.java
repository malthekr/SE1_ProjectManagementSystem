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
    private Calendar startDate, endDate;
    private double workedHours;

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
    }

    // Removes employee from activity
    public void removeEmployee(Employee employee) throws OperationNotAllowedException {
        if (!employees.contains(employee)) {
            throw new OperationNotAllowedException("Employee is not part of activity");
        }
        employees.remove(employee);
        return;
    }

    public int getProjectId() {
        return projectId;
    }

    public void addWorkedHours(double hoursWorked) {
        hoursWorked = hoursWorked - (hoursWorked % 0.5);
        this.workedHours += hoursWorked;

        if (this.workedHours < 0) {
            this.workedHours = 0;
        }
<<<<<<< HEAD

        // return;
        // throw new OperationNotAllowedException("Worked hours is measured in half
        // hours worked");
=======
    	
    	return;
    	//throw new OperationNotAllowedException("Worked hours is measured in half hours worked");
>>>>>>> parent of 7c22330 (Removed old code/comments)
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public double getExpectedHours() {
        return expectedHours;
    }

    public void setDescrption(String description) {
        this.description = description;
    }

    public void setExpectedHours(double expectedHours) {
        this.expectedHours = 0 > expectedHours ? 0 : expectedHours - expectedHours % 0.5;
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
        if (this.getEmployees() != null) {
            boolean b = false;

            for (Employee a : getEmployees()) {
                b = a.getId().equals(searchText) || a.getName().equals(searchText) ? true : b;
            }

            return String.valueOf(getProjectId()).contains(searchText) || description.contains(searchText)
                    || getEmployees().contains(searchText) || b;
        } else {
            return description.contains(searchText);
        }
    }

    public String printDetail() {
        String name = this.getDescription().isBlank() ? "No description on activity" : this.getDescription();
        String id = this.getEmployees() == null ? "No employees assigned yet" : this.getEmployeesAsString();

        StringBuffer b = new StringBuffer();
        b.append("<html>" + "<br>");
        b.append(String.format("<b>Name:</b>     %s<br>", name));
        b.append(String.format("<b>Employee:</b>     %s<br>", id));
        b.append(String.format("<b>Project Id:</b>    %s<br>", this.getProjectId()));
        b.append(String.format("<b>Start date:</b>    %s<br>", this.getStartDate().getTime()));
        b.append(String.format("<b>End date:</b>    %s<br>", this.getEndDate().getTime()));
        b.append(String.format("<b>Expected Hours:</b> %s<br>", this.getExpectedHours()));
        b.append(String.format("<b>Worked Hours:</b> %s<br></html>", this.getWorkedHours()));
        return b.toString();
    }

    public String getEmployeesAsString() {
        String name = "";
        int i = 0;

        for (Employee e : this.getEmployees()) {
            name += e.getId();
            if (i++ == this.getEmployees().size() - 1) {
                break;
            }
            name += ", ";
        }
        return name;
    }

    public String toString() {
        String name = this.getDescription().isBlank() ? "" : this.getDescription();
        return "Project ID: " + String.valueOf(getProjectId()) + " Description: " + name;
    }

}
