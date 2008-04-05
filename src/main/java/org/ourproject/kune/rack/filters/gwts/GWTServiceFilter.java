package org.ourproject.kune.rack.filters.gwts;

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
import org.ourproject.kune.rack.RackHelper;
import org.ourproject.kune.rack.filters.InjectedFilter;

import com.google.gwt.user.client.rpc.RemoteService;

public class GWTServiceFilter extends InjectedFilter {
    public static final Log log = LogFactory.getLog(GWTServiceFilter.class);

    private final Class<? extends RemoteService> serviceClass;
    private final DelegatedRemoteServlet servlet;

    public GWTServiceFilter(final Class<? extends RemoteService> serviceClass) {
        this.serviceClass = serviceClass;
        this.servlet = new DelegatedRemoteServlet();
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        servlet.setServletContext(filterConfig.getServletContext());
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        log.debug("SERVICE: " + RackHelper.getURI(request) + " - " + serviceClass.getSimpleName());
        RemoteService service = getInstance(serviceClass);
        servlet.setService(service);
        servlet.doPost((HttpServletRequest) request, (HttpServletResponse) response);
    }

}
