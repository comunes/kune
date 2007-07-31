package to.tipit.gwtlib.firelog;

public abstract class FireLogImpl {
	public abstract void log(String message);
	public abstract void debug(String message);
	public abstract void info(String message);
	public abstract void warn(String message);
	public abstract void error(String message);
	public abstract void trace();
	public abstract void group(String message);
	public abstract void groupEnd();
	public abstract void time(String name);
	public abstract void timeEnd(String name);
	public abstract void profile(String title);
	public abstract void profileEnd();
	public abstract void count(String title);
	
	public static FireLogImpl getImpl(boolean isScript) {
		return isScript ? (FireLogImpl)new FireBug() : new HostedMode();
	}
}
