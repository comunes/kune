package to.tipit.gwtlib;

import com.google.gwt.core.client.GWT;

import to.tipit.gwtlib.firelog.FireLogImpl;

/**
 * Once GWT gains java5 notational support, all log messages will be expanded
 * to support 'printf'-like syntax properly.
 */
public class FireLog {
	private static FireLogImpl impl = FireLogImpl.getImpl(GWT.isScript());
	private static boolean enabled = true;
	
	public static void enable() {
		FireLog.enabled = true;
	}
	
	public static void disable() {
		FireLog.enabled = false;
	}
	
	public static void log(String message) {
		if ( enabled ) impl.log(message);
	}
	
	public static void debug(String message) {
		if ( enabled ) impl.debug(message);
	}
	
	public static void info(String message) {
		if ( enabled ) impl.info(message);
	}
	
	public static void warn(String message) {
		if ( enabled ) impl.warn(message);
	}
	
	public static void error(String message) {
		if ( enabled ) impl.error(message);
	}
	
	public static void trace() {
		if ( enabled ) impl.trace();
	}
	
	public static void group(String message) {
		if ( enabled ) impl.group(message);
	}
	
	public static void groupEnd() {
		if ( enabled ) impl.groupEnd();
	}
	
	public static void time(String name) {
		if ( enabled ) impl.time(name);
	}
	
	public static void timeEnd(String name) {
		if ( enabled ) impl.timeEnd(name);
	}
	
	public static void profile() {
		if ( enabled ) impl.profile(null);
	}
	
	public static void profile(String name) {
		if ( enabled ) impl.profile(name);
	}
	
	public static void profileEnd() {
		if ( enabled ) impl.profileEnd();
	}
	
	public static void count() {
		if ( enabled ) impl.count(null);
	}
	
	public static void count(String name) {
		if ( enabled ) impl.count(name);
	}
}
