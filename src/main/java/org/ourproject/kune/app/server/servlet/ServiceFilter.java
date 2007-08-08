package org.ourproject.kune.app.server.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.Injector;

public class ServiceFilter extends AbstractProcessor {
    private static final Log log = LogFactory.getLog(ServiceFilter.class);
    private final Class<? extends RemoteService> serviceClass;
    private final DelegatedRemoteServlet servlet;
    private final String relative;

    public ServiceFilter(final String appName, final String serviceName,
	    final Class<? extends RemoteService> serviceClass) {
	super(POST);
	this.serviceClass = serviceClass;
	this.relative = "/" + appName + "/" + serviceName;
	this.servlet = new DelegatedRemoteServlet();
    }

    @Override
    public void init(final ServletContext servletContext, final Injector injector) {
	super.init(servletContext, injector);
	servlet.setServletContext(servletContext);
    }

    @Override
    protected boolean process(final String relativeUrl, final HttpServletRequest request,
	    final HttpServletResponse response) throws IOException, ServletException {

	if (relative.equals(relativeUrl)) {
	    log.debug("CALLING THE SERVICE: " + relative);
	    RemoteService service = super.getInjector().getInstance(serviceClass);
	    servlet.setService(service);
	    servlet.doPost(request, response);
	    return true;
	}
	return false;
    }
}
