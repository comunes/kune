/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.waveprotocol.box.server.persistence.file.FileUtils;
import org.waveprotocol.box.server.rpc.ServerRpcProvider;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.error.ServerException;
import cc.kune.core.server.mbean.MBeanRegister;
import cc.kune.core.server.rack.dock.Dock;
import cc.kune.core.server.rack.dock.RequestMatcher;
import cc.kune.core.server.rack.utils.RackHelper;
import cc.kune.core.server.scheduler.CustomJobFactory;
import cc.kune.core.server.searcheable.SearchEngineServletFilter;

import com.google.gwt.logging.server.RemoteLoggingServiceImpl;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.grapher.GrapherModule;
import com.google.inject.grapher.InjectorGrapher;
import com.google.inject.grapher.graphviz.GraphvizModule;
import com.google.inject.grapher.graphviz.GraphvizRenderer;

public class RackServletFilter implements Filter {
  public static class DockChain implements FilterChain {
    private final Iterator<Dock> iterator;

    public DockChain(final Iterator<Dock> iterator) {
      this.iterator = iterator;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response)
        throws IOException, ServletException, DefaultException {
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

    private void execute(final Filter filter, final ServletRequest request,
        final ServletResponse response) throws IOException, ServletException {
      // log.debug("RACK FILTER: " + filter.getClass().getSimpleName());
      filter.doFilter(request, response, this);
    }
  }

  public static final String INJECTOR_ATTRIBUTE = Injector.class.getName() + "Child";

  public static final String INJECTOR_PARENT_ATTRIBUTE = ServerRpcProvider.INJECTOR_ATTRIBUTE;

  private static final Log LOG = LogFactory.getLog(RackServletFilter.class);
  private static final String MODULE_PARAMETER = RackModule.class.getName();
  private static final String SYMBOL_MAPS_ON_DEV = "target/kune-0.3.0-SNAPSHOT/WEB-INF/deploy/wse/symbolMaps";
  private static final String SYMBOL_MAPS_ON_PRODUCTION = "symbolMapsWse";
  private List<Dock> docks;

  private List<RequestMatcher> excludes;
  private boolean initialized;
  private Injector injector;

  private Rack rack;

  @Override
  public void destroy() {
    for (final Dock dock : docks) {
      dock.getFilter().destroy();
    }
  }

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response,
      final FilterChain chain) throws IOException, ServletException {

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

  @SuppressWarnings("unused")
  private void graph(final String filename, final Injector kuneInjector) {
    try {
      final PrintWriter out = new PrintWriter(new File(filename), "UTF-8");

      final Injector injector = Guice.createInjector(new GrapherModule(), new GraphvizModule());
      final GraphvizRenderer renderer = injector.getInstance(GraphvizRenderer.class);
      renderer.setOut(out).setRankdir("TB");
      injector.getInstance(InjectorGrapher.class).of(kuneInjector).graph();
    } catch (final IOException e) {
      LOG.debug("Exception creation guice graph", e);
    }
  }

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    if (initialized) {
      throw new ServerException("Trying to init RackServletFilter twice");
    }
    LOG.debug("INITIALIZING RackServletFilter...");
    final RackModule module = getModule(filterConfig);
    final RackBuilder builder = new RackBuilder();
    module.configure(builder);
    rack = builder.getRack();
    injector = (Injector) filterConfig.getServletContext().getAttribute(INJECTOR_PARENT_ATTRIBUTE);

    final Module otherGuiceModule = new Module() {
      @Override
      public void configure(final Binder binder) {
        // Here, other objects that are not register in Rack
        binder.bind(SearchEngineServletFilter.class).toInstance(
            (SearchEngineServletFilter) filterConfig.getServletContext().getAttribute(
                SearchEngineServletFilter.SEARCH_ENGINE_FILTER_ATTRIBUTE));
      }
    };

    final Injector kuneChildInjector = installInjector(filterConfig, rack, injector, otherGuiceModule);
    final CustomJobFactory jobFactory = kuneChildInjector.getInstance(CustomJobFactory.class);
    jobFactory.setInjector(kuneChildInjector);
    startContainerListeners(rack.getListeners(), kuneChildInjector);
    docks = rack.getDocks();
    excludes = rack.getExcludes();
    initFilters(filterConfig);
    LOG.debug("INITIALIZATION DONE!");

    // kuneChildInjector.getInstance(CustomImportServlet.class).init(
    // kuneChildInjector.getInstance(KuneProperties.class));

    LOG.debug("Register some mbeans objects");
    kuneChildInjector.getInstance(MBeanRegister.class);

    LOG.debug("Configure remote logging");
    final String dir = FileUtils.isDirExistsAndNonEmpty(SYMBOL_MAPS_ON_PRODUCTION) ? "symbolMapsWse/"
        : FileUtils.isDirExistsAndNonEmpty(SYMBOL_MAPS_ON_DEV) ? SYMBOL_MAPS_ON_DEV : null;
    if (dir != null) {
      kuneChildInjector.getInstance(RemoteLoggingServiceImpl.class).setSymbolMapsDirectory(dir);
    }

    initialized = true;

    // Uncomment to generate the graph
    // graph("docs/wave-guice-graph.dot", injector);
    // graph("docs/kune-guice-graph.dot", kuneChildInjector);
  }

  private void initFilters(final FilterConfig filterConfig) throws ServletException {
    for (final Dock dock : docks) {
      dock.getFilter().init(filterConfig);
    }
  }

  private Injector installInjector(final FilterConfig filterConfig, final Rack rack,
      final Injector waveChildInjector, final Module otherModule) {
    final List<Module> guiceModules = rack.getGuiceModules();
    guiceModules.add(otherModule);
    final Injector childInjector = waveChildInjector.createChildInjector(guiceModules);
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

  private void stopContainerListeners(final List<Class<? extends ContainerListener>> listenerClasses,
      final Injector injector) {
    LOG.debug("STOPPING CONTAINER LISTENERS...");
    for (final Class<? extends ContainerListener> listenerClass : listenerClasses) {
      final ContainerListener listener = injector.getInstance(listenerClass);
      listener.stop();
    }
  }
}
