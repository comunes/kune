package org.ourproject.kune.rack.dock;

import javax.servlet.Filter;


public abstract class AbstractDock implements Dock {
	private Filter filter;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

}
