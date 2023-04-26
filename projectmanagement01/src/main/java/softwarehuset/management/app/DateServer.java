package softwarehuset.management.app;

public class DateServer {
	public Calendar getDate() {
		Calendar calendar = new GregorianCalendar();
		Calendar c = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		return c;
	} 
	
	public Calendar getYear() {
		Calendar calendar = new GregorianCalendar();
		Calendar c = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		return c;
	}
	
	public Calendar add(Calendar date, int days) {
		Calendar nextDate = new GregorianCalendar();
		nextDate.setTime(date.getTime());
		nextDate.add(Calendar.DAY_OF_YEAR, days);
		//when(dateServer.getDate()).thenReturn(nextDate);
		
		return nextDate;
	}
}
