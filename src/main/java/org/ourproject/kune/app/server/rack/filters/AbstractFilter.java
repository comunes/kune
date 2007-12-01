package org.ourproject.kune.app.server.rack.filters;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.ourproject.kune.app.server.rack.RackHelper;
import org.ourproject.kune.app.server.rack.RackServletFilter;

import com.google.inject.Injector;

public abstract class AbstractFilter implements Filter {
	private ServletContext ctx;

	public <T> T getInstance(Class<T> type) {
		return getInjector().getInstance(type);
	}

	public Injector getInjector() {
		return (Injector) ctx.getAttribute(RackServletFilter.INJECTOR_ATTRIBUTE);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.ctx = filterConfig.getServletContext();
	}

	public void destroy() {
	}

	public String buildForwardString(ServletRequest request, String forward) {
		String parameters = RackHelper.extractParameters(request);
		return new StringBuilder(forward).append(parameters).toString();
	}



}
