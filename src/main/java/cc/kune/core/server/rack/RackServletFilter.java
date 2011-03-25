/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.rack;

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
import org.waveprotocol.box.server.rpc.ServerRpcProvider;

import cc.kune.core.server.ServerException;
import cc.kune.core.server.rack.dock.Dock;
import cc.kune.core.server.rack.dock.RequestMatcher;

import com.google.inject.Injector;

public class RackServletFilter implements Filter {
    public static class DockChain implements FilterChain {
        private final Iterator<Dock> iterator;

        public DockChain(final Iterator<Dock> iterator) {
            this.iterator = iterator;
        }

        @Override
        public void doFilter(final ServletRequest request, final ServletResponse response) throws IOException,
                ServletException {
            Dock dock = null;
            boolean matched = false;

            final String relative = RackHelper.getRelativeURL(request);
            while (!matched && iterator.hasNext()) {
                dock = iterator.next();
                matched = dock.matches(relative);
            }
            if (matched) {
                execute(dock.getFilter(), request, response);
            }
        }

        private void execute(final Filter filter, final ServletRequest request, final ServletResponse response)
                throws IOException, ServletException {
            // log.debug("RACK FILTER: " + filter.getClass().getSimpleName());
            filter.doFilter(request, response, this);
        }
    }

    public static final String INJECTOR_ATTRIBUTE = Injector.class.getName() + "Child";
    public static final String INJECTOR_PARENT_ATTRIBUTE = ServerRpcProvider.INJECTOR_ATTRIBUTE;
    private static final Log LOG = LogFactory.getLog(RackServletFilter.class);
    private static final String MODULE_PARAMETER = RackModule.class.getName();
    private List<Dock> docks;

    private List<RequestMatcher> excludes;
    private Injector injector;
    private Rack rack;

    @Override
    public void destroy() {
        for (final Dock dock : docks) {
            dock.getFilter().destroy();
        }
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        final String relative = RackHelper.getRelativeURL(request);
        for (final RequestMatcher matcher : excludes) {
            if (matcher.matches(relative)) {
                LOG.info("Excluded (from Guice): " + relative);
                chain.doFilter(request, response);
                return;
            }
        }
        LOG.debug("REQUEST: " + relative);
        final FilterChain newChain = new DockChain(docks.iterator());
        newChain.doFilter(request, response);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        stopContainerListeners(rack.getListeners(), injector);
    }

    private RackModule getModule(final FilterConfig filterConfig) {
        final String moduleName = getModuleName(filterConfig);
        try {
            final Class<?> clazz = Class.forName(moduleName);
            final RackModule module = (RackModule) clazz.newInstance();
            return module;
        } catch (final Exception e) {
            throw new ServerException("couldn't instantiate the rack module", e);
        }
    }

    private String getModuleName(final FilterConfig filterConfig) {
        final String moduleName = filterConfig.getInitParameter(MODULE_PARAMETER);
        if (moduleName == null) {
            throw new ServerException("Rack module name can't be null!");
        }
        return moduleName;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        LOG.debug("INITIALIZING RackServletFilter...");
        final RackModule module = getModule(filterConfig);
        final RackBuilder builder = new RackBuilder();
        module.configure(builder);
        rack = builder.getRack();
        // final WaveStarter waveStarter = new WaveStarter();
        // final Injector waveChildInjector = waveStarter.runMain();
        injector = (Injector) filterConfig.getServletContext().getAttribute(INJECTOR_PARENT_ATTRIBUTE);
        final Injector kuneChildInjector = installInjector(filterConfig, rack, injector);
        startContainerListeners(rack.getListeners(), kuneChildInjector);
        docks = rack.getDocks();
        excludes = rack.getExcludes();
        initFilters(filterConfig);
        LOG.debug("INITIALIZATION DONE!");
    }

    private void initFilters(final FilterConfig filterConfig) throws ServletException {
        for (final Dock dock : docks) {
            dock.getFilter().init(filterConfig);
        }
    }

    private Injector installInjector(final FilterConfig filterConfig, final Rack rack, final Injector waveChildInjector) {
        // final Injector injector = Guice.createInjector();
        final Injector childInjector = waveChildInjector.createChildInjector(rack.getGuiceModules());
        filterConfig.getServletContext().setAttribute(INJECTOR_ATTRIBUTE, childInjector);
        return childInjector;
    }

    private void startContainerListeners(final List<Class<? extends ContainerListener>> listenerClasses,
            final Injector injector) {
        LOG.debug("STARTING CONTAINER LISTENERS...");
        for (final Class<? extends ContainerListener> listenerClass : listenerClasses) {
            final ContainerListener listener = injector.getInstance(listenerClass);
            listener.start();
        }
    }

    // FIXME: Dani, never used this:
    @SuppressWarnings("unused")
    private void stopContainerListeners(final List<Class<? extends ContainerListener>> listenerClasses,
            final Injector injector) {
        LOG.debug("STOPING CONTAINER LISTENERS...");
        for (final Class<? extends ContainerListener> listenerClass : listenerClasses) {
            final ContainerListener listener = injector.getInstance(listenerClass);
            listener.stop();
        }
    }

}
