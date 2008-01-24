package org.ourproject.kune.rack.dock;

import javax.servlet.ServletRequest;

public interface RequestMatcher {
	boolean matches(ServletRequest request);
}
