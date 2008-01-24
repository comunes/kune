/*
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

package org.ourproject.kune.rack;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.rack.dock.Dock;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class RackServletFilter implements Filter {
    private static final String MODULE_PARAMETER = RackModule.class.getName();
    private static final Log log = LogFactory.getLog(RackServletFilter.class);
    public static final String INJECTOR_ATTRIBUTE = Injector.class.getName();
    private List<Dock> docks;
    private String skipable;

    public static class DockChain implements FilterChain {
	private final Iterator<Dock> iterator;

	public DockChain(final Iterator<Dock> iterator) {
	    this.iterator = iterator;
	}

	public void doFilter(final ServletRequest request, final ServletResponse response) throws IOException,
		ServletException {
	    Dock dock = null;
	    boolean matched = false;

	    while (!matched && iterator.hasNext()) {
		dock = iterator.next();
		matched = dock.matches(request);
	    }
	    if (matched) {
		execute(dock.getFilter(), request, response);
	    }
	}

	private void execute(final Filter filter, final ServletRequest request, final ServletResponse response)
		throws IOException, ServletException {
	    log.debug("RACK FILTER: " + filter.getClass().getSimpleName());
	    filter.doFilter(request, response, this);
	}
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
	    throws IOException, ServletException {

	// log.debug("RACK: " + RackHelper.getURI(request));

	if (RackHelper.getRelativeURL(request).startsWith(skipable)) {
	    log.debug("RACK: skiping url!!!");
	    chain.doFilter(request, response);
	} else {
	    FilterChain newChain = new DockChain(docks.iterator());
	    newChain.doFilter(request, response);
	}
    }

    public void init(final FilterConfig filterConfig) throws ServletException {
	log.debug("INITIALIZING RackServletFilter...");
	RackModule module = getModule(filterConfig);
	RackBuilder builder = new RackBuilder();
	module.configure(builder);

	Rack rack = builder.getRack();
	Injector injector = installInjector(filterConfig, rack);
	startContainerListeners(rack.getListeners(), injector);

	docks = rack.getDocks();
	skipable = rack.getSkipable();
	initFilters(filterConfig);
	log.debug("INITIALIZATION DONE!");
    }

    private void startContainerListeners(final List<Class<? extends ContainerListener>> listenerClasses,
	    final Injector injector) {
	log.debug("STARTING CONTAINER LISTENERS...");
	for (Class<? extends ContainerListener> listenerClass : listenerClasses) {
	    ContainerListener listener = injector.getInstance(listenerClass);
	    listener.start();
	}
    }

    private void stopContainerListeners(final List<Class<? extends ContainerListener>> listenerClasses,
	    final Injector injector) {
	log.debug("STOPING CONTAINER LISTENERS...");
	for (Class<? extends ContainerListener> listenerClass : listenerClasses) {
	    ContainerListener listener = injector.getInstance(listenerClass);
	    listener.stop();
	}
    }

    private Injector installInjector(final FilterConfig filterConfig, final Rack rack) {
	Injector injector = Guice.createInjector(rack.getGuiceModules());
	filterConfig.getServletContext().setAttribute(INJECTOR_ATTRIBUTE, injector);
	return injector;
    }

    private void initFilters(final FilterConfig filterConfig) throws ServletException {
	for (Dock dock : docks) {
	    dock.getFilter().init(filterConfig);
	}
    }

    public void destroy() {
	for (Dock dock : docks) {
	    dock.getFilter().destroy();
	}
    }

    private RackModule getModule(final FilterConfig filterConfig) {
	String moduleName = getModuleName(filterConfig);
	try {
	    Class<?> clazz = Class.forName(moduleName);
	    RackModule module = (RackModule) clazz.newInstance();
	    return module;
	} catch (Exception e) {
	    throw new RuntimeException("couldn't instantiate the rack module", e);
	}
    }

    private String getModuleName(final FilterConfig filterConfig) {
	String moduleName = filterConfig.getInitParameter(MODULE_PARAMETER);
	if (moduleName == null) {
	    throw new RuntimeException("Rack module name can't be null!");
	}
	return moduleName;
    }

}
