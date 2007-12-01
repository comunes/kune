package org.ourproject.kune.app.server.rack;

import javax.servlet.Filter;

public abstract class AbstractRackDock implements Dock {
	private Filter filter;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

}
