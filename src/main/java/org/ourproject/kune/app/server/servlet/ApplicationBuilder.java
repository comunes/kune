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

import com.google.inject.Module;

public class ApplicationBuilder {
    private final ArrayList<Module> modules;
    private final ArrayList<Application> apps;

    public ApplicationBuilder() {
	modules = new ArrayList<Module>();
	apps = new ArrayList<Application>();
    }

    public void use(final Module module) {
	modules.add(module);
    }

    public Module[] getModules() {
	return modules.toArray(new Module[modules.size()]);
    }

    public Application create(final String appName, final String defaultFile, final String homePath) {
	Application app = new Application(appName, defaultFile, homePath);
	apps.add(app);
	return app;
    }

    public Application getApplication() {
	return apps.get(0);
    }

}
