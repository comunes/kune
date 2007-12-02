package org.ourproject.rack.dock;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;

/**
 * A filter with a matcher ... se RegexDock for a uri regex implementation
 */
public interface Dock {
	public void setFilter(Filter filter);
	Filter getFilter();
	boolean matches(ServletRequest request);
}
