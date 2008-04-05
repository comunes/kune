package org.ourproject.kune.rack.dock;

import javax.servlet.Filter;

/**
 * A filter with a matcher ... se RegexDock for a uri regex implementation
 */
public interface Dock extends RequestMatcher {
	public void setFilter(Filter filter);

	Filter getFilter();
}
