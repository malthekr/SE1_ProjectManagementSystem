package softwarehuset.management.app;

import java.util.Calendar;

public class Caldendar_Server {

	// Create a specific date as dd-mm-yyyy
	public Calendar setDate(Calendar date, int dd, int mm, int yyyy) {
		date.set(Calendar.YEAR, yyyy);
		date.set(Calendar.MONTH, mm);
		date.set(Calendar.DAY_OF_MONTH, dd);
		return date;
	}

}
