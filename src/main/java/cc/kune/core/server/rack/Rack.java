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
/*
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
 \*/
package cc.kune.core.server.rack;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.rack.dock.Dock;
import cc.kune.core.server.rack.dock.RequestMatcher;

import com.google.inject.Module;

public class Rack {
  public static final Log LOG = LogFactory.getLog(Rack.class);
  private final ArrayList<Dock> docks;
  private final ArrayList<RequestMatcher> excludes;
  private final ArrayList<Class<? extends ContainerListener>> listeners;
  private final ArrayList<Module> modules;

  public Rack() {
    this.docks = new ArrayList<Dock>();
    this.modules = new ArrayList<Module>();
    this.excludes = new ArrayList<RequestMatcher>();
    this.listeners = new ArrayList<Class<? extends ContainerListener>>();
  }

  public void add(final Class<? extends ContainerListener> listenerType) {
    listeners.add(listenerType);
  }

  public void add(final Dock dock) {
    LOG.debug("INSTALLING: " + dock.toString());
    docks.add(dock);
  }

  public void add(final Module module) {
    modules.add(module);
  }

  public void addExclusion(final RequestMatcher matcher) {
    excludes.add(matcher);
  }

  public List<Dock> getDocks() {
    return docks;
  }

  public List<RequestMatcher> getExcludes() {
    return excludes;
  }

  public List<Module> getGuiceModules() {
    return modules;
  }

  public List<Class<? extends ContainerListener>> getListeners() {
    return listeners;
  }

}
