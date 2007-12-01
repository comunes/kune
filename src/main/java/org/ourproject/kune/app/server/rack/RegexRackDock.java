package org.ourproject.kune.app.server.rack;

import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

public class RegexRackDock extends AbstractRackDock {
//	private static final Log log = LogFactory.getLog(RegexRackDock.class);
	private Pattern pattern;

	public RegexRackDock(String regex) {
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
