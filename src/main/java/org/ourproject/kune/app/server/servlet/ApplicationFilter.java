package org.ourproject.kune.app.server.servlet;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApplicationFilter {
    Log log = LogFactory.getLog(ApplicationFilter.class);
    private final String applicationName;
    private final String appHome;
    private int ctxPrefix;
    private final String defaultFile;
    private String home;

    public ApplicationFilter(final String applicationName, final String defaultFile, final String appHome) {
	this.applicationName = "/" + applicationName;
	this.defaultFile = defaultFile;
	this.appHome = appHome;
    }

    public void init(final FilterConfig config) {
	String ctxName = config.getServletContext().getServletContextName();
	this.home = "/" + appHome;
	this.ctxPrefix = ctxName.length() + 1;
	log.debug("CTXNAME: " + ctxName);
	log.debug("CTXPREFIX: " + ctxPrefix);
    }

    public boolean doFilter(final HttpServletRequest req, final HttpServletResponse response) throws IOException,
	    ServletException {
	String contextPath = req.getContextPath();
	String uri = req.getRequestURI();
	log.debug("CTX PATH: " + contextPath);
	log.debug("URI: " + uri);

	String path = uri.substring(contextPath.length());
	log.debug("PATH: " + path);

	if (path.startsWith(applicationName)) {
	    String rest = path.substring(applicationName.length());
	    log.debug("REST: " + rest);
	    if (rest.length() == 0) {
		log.debug("REDIRECT!");
		response.sendRedirect(uri + "/");
	    } else {
		if (rest.length() == 1) {
		    rest += defaultFile;
		}
		String forward = home + rest;
		req.getRequestDispatcher(forward).forward(req, response);
	    }
	    return true;
	}
	return false;
    }

}
