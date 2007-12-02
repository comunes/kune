package org.ourproject.rack.filters.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.rack.RackHelper;
import org.ourproject.rack.filters.InjectedFilter;

import com.google.inject.Inject;

public class RESTServiceFilter extends InjectedFilter {
	private static final Log log = LogFactory.getLog(RESTServiceFilter.class);

	public static class ParametersAdapter implements Parameters {
		private final ServletRequest request;

		public ParametersAdapter(ServletRequest request) {
			this.request = request;
		}

		public String get(String name) {
			return request.getParameter(name);
		}
		
	}
	
	private final Pattern pattern;
	private final Class<?> serviceClass;
	@Inject
	private RESTMethodFinder methodFinder;
	@Inject
	private RESTSerializer serializer;

	public RESTServiceFilter(String pattern, Class<?> serviceClass) {
		this.serviceClass = serviceClass;
		this.pattern = Pattern.compile(pattern);
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		String relativeURL = RackHelper.getRelativeURL(request);
		Matcher matcher = pattern.matcher(relativeURL);
		matcher.find();
		String methodName = matcher.group(1);
		log.debug("JSON METHOD: " + methodName + " on: " + serviceClass.getSimpleName());
		RESTMethod rest = methodFinder.findMethod(methodName, new ParametersAdapter(request), serviceClass);
		if (rest != null && rest.invoke(getInstance(serviceClass))) {
			String output = serializer.serialize(rest.getResponse(), rest.getFormat());
			write(response, output);
		}
	}

	private void write(ServletResponse response, String output) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.print(output);
		writer.flush();
	}



}
