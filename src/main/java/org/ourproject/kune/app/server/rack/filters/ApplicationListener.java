package org.ourproject.kune.app.server.rack.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ApplicationListener {
	void doBefore(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
	void doAfter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
