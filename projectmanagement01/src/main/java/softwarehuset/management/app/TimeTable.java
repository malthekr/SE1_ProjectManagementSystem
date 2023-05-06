package softwarehuset.management.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeTable {
	private Activity activity;
	private Employee employee;
	private Calendar date;
	private double workHours;
	
	public TimeTable(Activity activity, Employee employee, double workHours) {
		this.activity = activity;
		this.employee = employee;
		this.date = generateDate();
		this.workHours = workHours - workHours % 0.5;
		
	}
	
	public Activity getActivity() {
		return this.activity;
	}
	
	public Employee getEmployee() {
		return this.employee;
	}
	
	public Calendar getDate() {
		return this.date;
	}
	
	public double getHoursWorked() {
		return this.workHours;
	}
	
	public void editActivity(Activity newActivity) {
		this.activity = newActivity;
	}
	
	public void editEmployee(Employee newEmployee) {
		this.employee = newEmployee;
	}
	
	public void editDate(Calendar newDate) {
		this.date = newDate;
	}
	
	public void editHours(double newHours) {
		this.workHours = newHours - newHours % 0.5;
	}
	
	public Calendar generateDate(){
		Calendar dateToday = new GregorianCalendar();
		
		return dateToday;
	}
}
