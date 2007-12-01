package org.ourproject.rack.dock;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;

public interface Dock {
	public void setFilter(Filter filter);
	Filter getFilter();
	boolean matches(ServletRequest request);
}
