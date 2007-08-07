package org.ourproject.kune.app.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.platf.server.KuneServiceDefault;

public class GWTFilter implements Filter {
    private static final Log log = LogFactory.getLog(GWTFilter.class);
    private final List<SimpleFilter> filters;

    public GWTFilter() {
	filters = new ArrayList<SimpleFilter>();
	filters.add(new ApplicationFilter("kune", "Kune.html", "gwt/org.ourproject.kune.app.Kune"));
	filters.add(new ServiceFilter("kune", "KuneService", KuneServiceDefault.class));
    }

    public void init(final FilterConfig config) throws ServletException {
	for (SimpleFilter filter : filters) {
	    filter.init(config);
	}
    }

    public void destroy() {
	for (SimpleFilter filter : filters) {
	    filter.destroy();
	}
    }

    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
	    final FilterChain filterChain) throws IOException, ServletException {

	HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
	HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
	// log.debug("FILTER: " + httpRequest.getRequestURI());

	for (SimpleFilter filter : filters) {
	    if (filter.doFilter(httpRequest, httpResponse)) {
		return;
	    }
	}
	log.debug("NOT FILTERED!");
	filterChain.doFilter(servletRequest, servletResponse);
    }
}
