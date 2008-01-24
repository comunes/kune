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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.rack.dock.Dock;

import com.google.inject.Module;

public class Rack {
    public static final Log log = LogFactory.getLog(Rack.class);
    private final ArrayList<Dock> docks;
    private final ArrayList<Module> modules;
    private final ArrayList<Class<? extends ContainerListener>> listeners;
    private String skip;

    public Rack() {
	skip = null;
	this.docks = new ArrayList<Dock>();
	this.modules = new ArrayList<Module>();
	this.listeners = new ArrayList<Class<? extends ContainerListener>>();
    }

    public void add(final Dock dock) {
	log.debug("INSTALLING: " + dock.toString());
	docks.add(dock);
    }

    public void add(final Module module) {
	modules.add(module);
    }

    public List<Dock> getDocks() {
	return docks;
    }

    public List<Module> getGuiceModules() {
	return modules;
    }

    public void add(final Class<? extends ContainerListener> listenerType) {
	listeners.add(listenerType);
    }

    public List<Class<? extends ContainerListener>> getListeners() {
	return listeners;
    }

    // TODO
    public void addSkip(final String skipable) {
	if (skip != null) {
	    throw new RuntimeException("Este código es una chapuza para la demo!");
	} else {
	    skip = skipable;
	}
    }

    public String getSkipable() {
	return skip;
    }

}
