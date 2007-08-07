package org.ourproject.kune.app.server.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GWTFilter implements Filter {
    // private Injector injector;
    private final ApplicationFilter app;

    public GWTFilter() {
	app = new ApplicationFilter("edit", "Kune.html", "gwt/org.ourproject.kune.app.Kune");
    }

    public void init(final FilterConfig config) throws ServletException {
	app.init(config);
    }

    public void destroy() {
    }

    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
	    final FilterChain filterChain) throws IOException, ServletException {
	if (!app.doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse)) {
	    filterChain.doFilter(servletRequest, servletResponse);
	}
    }
}
