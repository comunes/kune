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

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

public class Application {
    private final ArrayList<SimpleFilter> filters;
    private final ArrayList<LifeCycleListener> cycleListeners;
    private final String currentAppName;
    private final ApplicationFilter currentApp;

    public Application(final String appName, final String defaultFile, final String homePath) {
	filters = new ArrayList<SimpleFilter>();
	cycleListeners = new ArrayList<LifeCycleListener>();
	currentAppName = appName;
	currentApp = new ApplicationFilter(appName, defaultFile, homePath);
	filters.add(currentApp);
    }

    public SimpleFilter[] getFilters() {
	return filters.toArray(new SimpleFilter[filters.size()]);
    }

    public Application useService(final String serviceName, final Class<? extends RemoteService> serviceClass) {
	filters.add(new ServiceFilter(currentAppName, serviceName, serviceClass));
	return this;
    }

    public void add(final LifeCycleListener listener) {
	cycleListeners.add(listener);
    }

    public LifeCycleListener[] getCycleListeners() {
	return cycleListeners.toArray(new LifeCycleListener[cycleListeners.size()]);
    }

    public void with(final Class<? extends ApplicationListener> appListenerType) {
	currentApp.setListener(appListenerType);
    }

}
