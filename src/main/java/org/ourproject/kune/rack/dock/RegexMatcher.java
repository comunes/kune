package org.ourproject.kune.rack.dock;

import java.util.regex.Pattern;

public class RegexMatcher implements RequestMatcher {
	private final Pattern pattern;

	public RegexMatcher(String stringPattern) {
		this(Pattern.compile(stringPattern));
	}
	
	public RegexMatcher(Pattern pattern) {
		this.pattern = pattern;
	}

	public boolean matches(String url) {
        return pattern.matcher(url).matches();
	}

}
