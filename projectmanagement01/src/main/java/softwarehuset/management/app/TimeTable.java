package softwarehuset.management.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeTable {
	private Activity activity;
	private Employee employee;
	private Calendar date;
	private double workHours;
	
	// Constructor of timetable under activity
	public TimeTable(Activity activity, Employee employee, double workHours) {
		this.activity = activity;
		this.employee = employee;
		this.date = generateDate();
		this.workHours = workHours - workHours % 0.5;
		
	}
	
	// Get activity time table is associated with
	public Activity getActivity() {
		return this.activity; 
	}
	
	// Get employee which time table is associated with
	public Employee getEmployee() {
		return this.employee;
	}
	
	// Get date of time table
	public Calendar getDate() {
		return this.date;
	}
	
	// Get hours worked on time table
	public double getHoursWorked() {
		return this.workHours;
	}
	
	// Edit which activity the time table is associated with
	public void editActivity(Activity newActivity) {
		this.activity = newActivity;
	}
	
	// Edit which employee the time table is associated with
	public void editEmployee(Employee newEmployee) {
		this.employee = newEmployee;
	}
	
	// Edit date of time table
	public void editDate(Calendar newDate) {
		this.date = newDate;
	}
	
	// Edit hours on time table
	public void editHours(double newHours) {
		this.workHours = newHours - newHours % 0.5;
	}
	
	// When creating the time table generate the date today
	public Calendar generateDate(){
		Calendar dateToday = new GregorianCalendar();
		
		return dateToday;
	}
}
