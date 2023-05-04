package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.Calendar;
<<<<<<< HEAD
import java.util.GregorianCalendar;
=======
import java.util.List;
>>>>>>> parent of 7c22330 (Removed old code/comments)

public class TimeTable {
	private Activity activity;
	private Employee employee;
	private Calendar date;
	private double workHours;

	public TimeTable(Activity activity, Employee employee, double workHours) {
		this.activity = activity;
		this.employee = employee;
		this.date = generateDate();
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

	public double getHoursWorked() {
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

	public void editHours(double newHours) {
		workHours = newHours;
	}

	public Calendar generateDate() {
		Calendar dateToday = new GregorianCalendar();
		// Calendar endDate1 = new GregorianCalendar();
		return dateToday;
	}
}
