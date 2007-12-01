package org.ourproject.rack.dock;

import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

import org.ourproject.rack.RackHelper;

public class RegexDock extends AbstractDock {
//	private static final Log log = LogFactory.getLog(RegexRackDock.class);
	private Pattern pattern;

	public RegexDock(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public boolean matches(ServletRequest request) {
		String relativeURL = RackHelper.getRelativeURL(request);
//		log.debug ("MATCHING: " + pattern.toString() + " AGAINST: " + relativeURL);
		return (pattern.matcher(relativeURL).matches());
	}
	
	@Override
	public String toString() {
		return pattern.toString() + " - " + getFilter().getClass().getSimpleName();
	}
	
}
