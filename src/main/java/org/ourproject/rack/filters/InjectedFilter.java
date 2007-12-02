package org.ourproject.rack.filters;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.ourproject.rack.RackServletFilter;

import com.google.inject.Injector;

public abstract class InjectedFilter implements Filter {
	private ServletContext ctx;

	public <T> T getInstance(Class<T> type) {
		return getInjector().getInstance(type);
	}

	private Injector getInjector() {
		return (Injector) ctx.getAttribute(RackServletFilter.INJECTOR_ATTRIBUTE);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.ctx = filterConfig.getServletContext();
		getInjector().injectMembers(this);
	}

	public void destroy() {
	}


}
