package org.ourproject.kune.app.server.rack.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListenerFilter extends AbstractFilter {
	private Class<? extends ApplicationListener> listenerClass;

	public ListenerFilter(Class<? extends ApplicationListener> listenerClass) {
		this.listenerClass = listenerClass;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		ApplicationListener listener = super.getInstance(listenerClass);
		listener.doBefore((HttpServletRequest) request, (HttpServletResponse) response);
		chain.doFilter(request, response);
		listener.doAfter((HttpServletRequest) request, (HttpServletResponse) response);
	}

}
