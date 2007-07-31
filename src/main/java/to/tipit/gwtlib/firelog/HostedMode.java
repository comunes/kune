package to.tipit.gwtlib.firelog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HostedMode extends FireLogImpl {
	private Map markerStore = new HashMap();
	
	public void count(String title) {
		StackTraceElement line = new Throwable().getStackTrace()[2];
		String marker = line.getClassName() + ":::" + line.getLineNumber();
		int count = 1;
		
		if ( markerStore.containsKey(marker) )
			count += ((Integer)markerStore.get(marker)).intValue();
		
		markerStore.put(marker, new Integer(count));
		
		if ( title == null ) title = toLine(line);
		
		log(title + ": " + count);
	}
	
	private String toLine(StackTraceElement line) {
		return line.getClassName() + "(" + line.getLineNumber() + ")";
	}
	
	public void debug(String message) {
		StackTraceElement line = new Throwable().getStackTrace()[2];
		log("DEBUG " + toLine(line) + ": " + message);
	}
	
	public void error(String message) {
		StackTraceElement line = new Throwable().getStackTrace()[2];
		log("ERROR " + toLine(line) + ": " + message);
	}
	
	private List groups = new ArrayList();
	
	public void group(String message) {
		groups.add(message);
		log("------START: " + message);
	}
	
	public void groupEnd() {
		if ( groups.size() == 0 ) log("FIRELOG ERROR: " +
				toLine(new Throwable().getStackTrace()[2]) +
				": group list empty; groupEnd makes no sense here");
		else log("------START: " + groups.remove(groups.size() -1));
	}
	
	public void info(String message) {
		StackTraceElement line = new Throwable().getStackTrace()[2];
		log("INFO " + toLine(line) + ": " + message);
	}
	
	public void log(String message) {
		System.out.println(message);
	}
	
	public void profile(String title) {
		log("Profiling not supported in HostedMode FireBug yet :(");
	}
	
	public void profileEnd() {
	}
	
	private Map timerStore = new HashMap();
	public void time(String name) {
		timerStore.put(name, new Long(System.currentTimeMillis()));
	}
	
	public void timeEnd(String name) {
		if ( timerStore.containsKey(name) ) {
			long end = System.currentTimeMillis();
			long start = ((Long)timerStore.remove(name)).longValue();
			long diff = end - start;
			System.out.println("TIMER " + name + ": " + diff + " millis");
		} else log("FIRELOG ERROR: " +
				toLine(new Throwable().getStackTrace()[2]) +
				": unknown timer name: " + name);
	}
	
	public void trace() {
		new Throwable().printStackTrace();
	}
	
	public void warn(String message) {
		StackTraceElement line = new Throwable().getStackTrace()[2];
		log("WARN " + toLine(line) + ": " + message);
	}
}
