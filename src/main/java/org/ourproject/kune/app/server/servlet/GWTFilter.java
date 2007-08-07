package org.ourproject.kune.app.server.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.ourproject.kune.platf.server.servlet.KuneServletContext;

import com.google.inject.Injector;

public class GWTFilter implements Filter {
    private Injector injector;

    public void init(final FilterConfig config) throws ServletException {
	injector = KuneServletContext.getInjector(config.getServletContext());
    }

    public void destroy() {
    }

    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain)
	    throws IOException, ServletException {

    }
}
