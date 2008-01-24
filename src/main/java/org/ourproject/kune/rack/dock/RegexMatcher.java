package org.ourproject.kune.rack.dock;

import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

import org.ourproject.kune.rack.RackHelper;

public class RegexMatcher implements RequestMatcher {
	private final Pattern pattern;

	public RegexMatcher(String stringPattern) {
		this(Pattern.compile(stringPattern));
	}
	
	public RegexMatcher(Pattern pattern) {
		this.pattern = pattern;
	}

	public boolean matches(ServletRequest request) {
		return RackHelper.matches(request, pattern);
	}

}
