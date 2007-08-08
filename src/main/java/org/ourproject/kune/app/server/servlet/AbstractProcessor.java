package org.ourproject.kune.app.server.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Injector;

public abstract class AbstractProcessor implements SimpleFilter {
    public static final Log log = LogFactory.getLog(AbstractProcessor.class);
    protected ServletContext servletContext;
    private final String[] validMethods;
    protected Injector injector;

    public AbstractProcessor(final String... validMethods) {
	this.validMethods = validMethods;
    }

    public void init(final ServletContext servletContext, final Injector injector) {
	this.servletContext = servletContext;
	this.injector = injector;
    }

    public void destroy() {
    }

    public ServletContext getServletContext() {
	return servletContext;
    }

    public Injector getInjector() {
	return injector;
    }

    public boolean doFilter(final HttpServletRequest req, final HttpServletResponse response) throws IOException,
	    ServletException {

	if (isValidMethod(req)) {
	    String contextPath = req.getContextPath();
	    String uri = req.getRequestURI();
	    String relativeUrl = uri.substring(contextPath.length());
	    return process(relativeUrl, req, response);
	}
	return false;
    }

    private boolean isValidMethod(final HttpServletRequest req) {
	String method = req.getMethod();
	for (String valid : validMethods) {
	    if (valid.equals(method)) {
		return true;
	    }
	}
	return false;
    }

    protected abstract boolean process(final String relativeUrl, final HttpServletRequest request,
	    final HttpServletResponse response) throws IOException, ServletException;

}
