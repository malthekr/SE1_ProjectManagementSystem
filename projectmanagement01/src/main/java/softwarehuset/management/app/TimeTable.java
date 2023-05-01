package softwarehuset.management.app;

import java.util.Calendar;

public class TimeTable {
	private Activity activity;
	private Employee employee;
	private Calendar date;
	private int workHours;
	
	public TimeTable(Activity activity, Employee employee, Calendar date, int workHours) {
		this.activity = activity;
		this.employee = employee;
		this.date = date;
		this.workHours = workHours;
	}
	
	public Activity getActivity() {
		return activity;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public int getHoursWorked() {
		return workHours;
	}
	
	public void editActivity(Activity newActivity) {
		activity = newActivity;
	}
	
	public void editEmployee(Employee newEmployee) {
		employee = newEmployee;
	}
	
	public void editDate(Calendar newDate) {
		date = newDate;
	}
	
	public void editHours(int newHours) {
		workHours = newHours;
	}
}
