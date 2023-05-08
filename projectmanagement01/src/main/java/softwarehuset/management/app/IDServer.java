package softwarehuset.management.app;

import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// [NIKLAS]
public class IDServer {
	private static ConcurrentHashMap<Integer, AtomicInteger> counters = new ConcurrentHashMap<>();
	
	// Generate a new unique project id number 
	public int generateID(Calendar startDate) {
		int year = startDate.get(Calendar.YEAR) % 100;
		
		AtomicInteger counter = counters.computeIfAbsent(year, y -> new AtomicInteger(0));
		
        int number = counter.incrementAndGet();
        int id = year * 1000 + number;
		
		return id;
	}
}