package org.ourproject.kune.rack.filters.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.rack.RackHelper;
import org.ourproject.kune.rack.filters.InjectedFilter;

public class ServletServiceFilter extends InjectedFilter {
    Log log = LogFactory.getLog(ServletServiceFilter.class);
    private final Class<? extends HttpServlet> servletClass;

    public ServletServiceFilter(final Class<? extends HttpServlet> servletClass) {
        this.servletClass = servletClass;
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        log.debug("SERVICE: " + RackHelper.getURI(request) + " - " + servletClass.getSimpleName());
        final HttpServlet servlet = getInstance(servletClass);
        servlet.service(request, response);
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }

}
