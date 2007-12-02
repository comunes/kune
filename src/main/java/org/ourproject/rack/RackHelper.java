package org.ourproject.rack;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RackHelper {
	private RackHelper() {
	}
	
	public static String extractParameters(ServletRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String uri = httpServletRequest.getRequestURI();
		int index = uri.indexOf('?');
		if (index > 0) {
			return uri.substring(index);
		} else {
			return "";
		}
	}
	
	public static String getRelativeURL(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
	    String contextPath = req.getContextPath();
	    String uri = req.getRequestURI();
	    return uri.substring(contextPath.length());
	}

	public static String getURI(ServletRequest request) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		return httpServletRequest.getRequestURI();
	}

	public static String buildForwardString(ServletRequest request, String forward) {
		String parameters = RackHelper.extractParameters(request);
		return new StringBuilder(forward).append(parameters).toString();
	}

}
