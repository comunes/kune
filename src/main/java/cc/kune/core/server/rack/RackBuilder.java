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

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import cc.kune.core.server.rack.dock.RegexDock;
import cc.kune.core.server.rack.dock.RegexMatcher;
import cc.kune.core.server.rack.filters.gwts.GWTServiceFilter;
import cc.kune.core.server.rack.filters.rest.CORSServiceFilter;
import cc.kune.core.server.rack.filters.rest.RESTServiceFilter;
import cc.kune.core.server.rack.filters.servlet.ServletServiceFilter;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.Module;

public class RackBuilder {

  public static class RackDockBuilder {
    private final Rack rack;
    private final String regex;

    public RackDockBuilder(final Rack rack, final String regex) {
      this.rack = rack;
      this.regex = regex;
    }

    public RackDockBuilder install(final Filter... filters) {
      for (final Filter filter : filters) {
        final RegexDock dock = new RegexDock(regex);
        dock.setFilter(filter);
        rack.add(dock);
      }
      return this;
    }

  }

  private final Rack rack;

  public RackBuilder() {
    this.rack = new Rack();
  }

  public RackBuilder add(final Class<? extends ContainerListener> listener) {
    rack.add(listener);
    return this;
  }

  public RackDockBuilder at(final String regex) {
    return new RackDockBuilder(rack, regex);
  }

  public RackBuilder exclude(final String... excludes) {
    for (final String exclude : excludes) {
      rack.addExclusion(new RegexMatcher(exclude));
    }
    return this;
  }

  public Rack getRack() {
    return rack;
  }

  public void installCORSServices(final String root, final Class<?>... serviceClasses) {
    for (final Class<?> serviceClass : serviceClasses) {
      final String simpleName = serviceClass.getSimpleName();
      final String pattern = root + simpleName + "/(.*)$";
      final RegexDock dock = new RegexDock(pattern);
      dock.setFilter(new CORSServiceFilter(pattern, serviceClass));
      rack.add(dock);
    }
  }

  public RackBuilder installGWTServices(final String root,
      final Class<? extends RemoteService>... serviceClasses) {

    for (final Class<? extends RemoteService> serviceClass : serviceClasses) {
      final String simpleName = serviceClass.getSimpleName();
      final RegexDock dock = new RegexDock(root + simpleName + "$");
      dock.setFilter(new GWTServiceFilter(serviceClass));
      rack.add(dock);
    }

    return this;
  }

  public void installRESTServices(final String root, final Class<?>... serviceClasses) {
    for (final Class<?> serviceClass : serviceClasses) {
      final String simpleName = serviceClass.getSimpleName();
      final String pattern = root + simpleName + "/(.*)$";
      final RegexDock dock = new RegexDock(pattern);
      dock.setFilter(new RESTServiceFilter(pattern, serviceClass));
      rack.add(dock);
    }
  }

  public void installServlet(final String root, final Class<? extends HttpServlet>... servletClasses) {
    for (final Class<? extends HttpServlet> servletClass : servletClasses) {
      final String simpleName = servletClass.getSimpleName();
      final RegexDock dock = new RegexDock(root + simpleName + "$");
      dock.setFilter(new ServletServiceFilter(servletClass));
      rack.add(dock);
    }

  }

  public RackBuilder use(final Module... list) {
    for (final Module m : list) {
      rack.add(m);
    }
    return this;
  }
}
