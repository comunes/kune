package org.ourproject.rack.filters.rest;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.rack.RackHelper;
import org.ourproject.rack.filters.AbstractFilter;

public class JSONServiceFilter extends AbstractFilter {
	private static final Log log = LogFactory.getLog(JSONServiceFilter.class);

	private final Pattern pattern;
	private final Class<?> serviceClass;
	private RESTMethodFinder methodFinder;

	public JSONServiceFilter(String pattern, Class<?> serviceClass) {
		this.serviceClass = serviceClass;
		this.pattern = Pattern.compile(pattern);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		this.methodFinder = getInstance(RESTMethodFinder.class);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		String relativeURL = RackHelper.getRelativeURL(request);
		Matcher matcher = pattern.matcher(relativeURL);
		matcher.find();
		String methodName = matcher.group(1);
		log.debug("JSON METHOD: " + methodName + " on: " + serviceClass.getSimpleName());



	}



}
