package org.ourproject.rack.filters.gwts;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.rack.RackHelper;
import org.ourproject.rack.filters.InjectedFilter;

import com.google.gwt.user.client.rpc.RemoteService;

public class GWTServiceFilter extends InjectedFilter {
	public static final Log log = LogFactory.getLog(GWTServiceFilter.class);
	
	private final Class<? extends RemoteService> serviceClass;
	private final DelegatedRemoteServlet servlet;

	public GWTServiceFilter(Class<? extends RemoteService> serviceClass) {
		this.serviceClass = serviceClass;
		this.servlet = new DelegatedRemoteServlet();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
        servlet.setServletContext(filterConfig.getServletContext());
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		log.debug("SERVICE: " + RackHelper.getURI(request) + " - " + serviceClass.getSimpleName()	);
		RemoteService service = getInstance(serviceClass);
        servlet.setService(service);
        servlet.doPost((HttpServletRequest) request, (HttpServletResponse) response);
	}

}
