/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.app.server.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class GWTFilter implements Filter {
    static final Log log = LogFactory.getLog(GWTFilter.class);
    private final SimpleFilter[] filters;
    private ServletContext servletContext;
    private final Module[] modules;
    private final LifeCycleListener[] listeners;
    private Injector injector;

    public GWTFilter() {
	ApplicationBuilder builder = new ApplicationBuilder();
	configure(builder);
	Application app = builder.getApplication();
	modules = builder.getModules();
	filters = app.getFilters();
	listeners = app.getCycleListeners();
    }

    protected abstract void configure(ApplicationBuilder builder);

    public void init(final FilterConfig config) throws ServletException {
	servletContext = config.getServletContext();
	injector = KuneServletContext.installInjector(servletContext, modules);
	startLifeListeners(servletContext);
	for (SimpleFilter filter : filters) {
	    filter.init(servletContext, injector);
	}
    }

    public void destroy() {
	for (SimpleFilter filter : filters) {
	    filter.destroy();
	}
	try {
	    stopLifeListeners(servletContext);
	} catch (ServletException e) {
	    e.printStackTrace();
	}
    }

    private void stopLifeListeners(final ServletContext servletContext) throws ServletException {
	for (LifeCycleListener lifeCycleListener : listeners) {
	    injector.injectMembers(lifeCycleListener);
	    lifeCycleListener.stop();
	}
    }

    private void startLifeListeners(final ServletContext servletContext) throws ServletException {
	for (LifeCycleListener lifeCycleListener : listeners) {
	    injector.injectMembers(lifeCycleListener);
	    lifeCycleListener.start();
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
	// log.debug("NOT FILTERED!");
	filterChain.doFilter(servletRequest, servletResponse);
    }
}
