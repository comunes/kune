package org.ourproject.rack.filters.json;

import java.io.IOException;
import java.lang.reflect.Method;
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
	private JSONMethodFinder methodFinder;

	public JSONServiceFilter(String pattern, Class<?> serviceClass) {
		this.serviceClass = serviceClass;
		this.pattern = Pattern.compile(pattern);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		this.methodFinder = getInstance(JSONMethodFinder.class);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		String relativeURL = RackHelper.getRelativeURL(request);
		Matcher matcher = pattern.matcher(relativeURL);
		matcher.find();
		String methodName = matcher.group(1);
		log.debug("JSON METHOD: " + methodName + " on: " + serviceClass.getSimpleName());

		Method method = findMethod(methodName, request);
		if (method != null) {
			try {
				Object result = method.invoke(getInstance(serviceClass), getCurrentParams());
				
			} catch (Exception e) {
				throw new RuntimeException("couldn't execute " + method, e);
			}
		} else {
			log.debug("JSON METHOD NOT FOUND!!");
		}

	}

	private Object[] getCurrentParams() {
		return null;
	}

	private Method findMethod(String methodName, ServletRequest request) {
		Method[] allMethods = serviceClass.getMethods();
		for (Method method : allMethods) {
			JSONMethod jsonMethod = method.getAnnotation(JSONMethod.class);
			if (jsonMethod != null && jsonMethod.name().equals(methodName)) {
				if (checkParams(jsonMethod, method, request)) {
					return method;
				}
			}
		}
		return null;
	}

	private boolean checkParams(JSONMethod jsonMethod, Method method, ServletRequest request) {
		for (String name: jsonMethod.params()) {
		}
		return false;
	}

}
