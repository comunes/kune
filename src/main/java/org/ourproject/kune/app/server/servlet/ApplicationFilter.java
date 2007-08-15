package org.ourproject.kune.app.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApplicationFilter extends AbstractProcessor {
    Log log = LogFactory.getLog(ApplicationFilter.class);
    private final String applicationName;
    private final String appHome;
    private final String defaultFile;
    private ApplicationListener appListener;

    public ApplicationFilter(final String applicationName, final String defaultFile, final String appHome) {
	super(SimpleFilter.GET);
	this.applicationName = "/" + applicationName;
	this.defaultFile = defaultFile;
	this.appHome = "/" + appHome;
    }

    protected boolean process(final String relativeUrl, final HttpServletRequest request,
	    final HttpServletResponse response) throws IOException, ServletException {
	if (relativeUrl.startsWith(applicationName)) {
	    String locator = relativeUrl.substring(applicationName.length());
	    if (locator.length() == 0) {
		log.debug("REDIRECT!");
		response.sendRedirect(request.getRequestURI() + "/");
	    } else {
		forward(request, response, locator);
	    }
	    return true;
	}
	return false;
    }

    private void forward(final HttpServletRequest request, final HttpServletResponse response, final String relativeURL)
	    throws ServletException, IOException {
	String forward;
	if (isHome(relativeURL)) {
	    callAppLifecycleListeners(request, response);
	    String extraParameters = extractParameters(relativeURL);
	    forward = appHome + relativeURL + defaultFile + extraParameters;
	} else {
	    forward = appHome + relativeURL;
	}
	request.getRequestDispatcher(forward).forward(request, response);
    }

    private String extractParameters(final String relativeURL) {
	int index = relativeURL.indexOf('?');
	if (index > 0) {
	    return relativeURL.substring(index);
	} else {
	    return "";
	}
    }

    private void callAppLifecycleListeners(final HttpServletRequest request, final HttpServletResponse response) {
	injector.injectMembers(appListener);
	if (appListener != null) {
	    appListener.onApplicationStart(request, response);
	}
    }

    private boolean isHome(final String relativeURL) {
	return relativeURL.length() == 1 && relativeURL.charAt(0) == '/';
    }

    public void setListener(final ApplicationListener appListener) {
	this.appListener = appListener;
    }

}
