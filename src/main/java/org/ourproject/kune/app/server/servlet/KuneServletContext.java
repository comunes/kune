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

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class KuneServletContext {
    private static final String KEY_IOC = Injector.class.getName();
    private static final String REMOTE_SERVICE = "remote.service";

    public static Injector getInjector(final ServletConfig config) throws ServletException {
	ServletContext ctx = config.getServletContext();
	return getInjector(ctx);
    }

    public static Injector getInjector(final ServletContext ctx) throws ServletException {
	Injector injector = (Injector) ctx.getAttribute(KEY_IOC);
	if (injector == null) {
	    injector = Guice.createInjector(createModule(ctx));
	    ctx.setAttribute(KEY_IOC, injector);
	}
	return injector;
    }

    public static Injector installInjector(final ServletContext ctx, final Module... modules) {
	Injector injector = Guice.createInjector(modules);
	ctx.setAttribute(KEY_IOC, injector);
	return injector;
    }

    public static Class<? extends RemoteService> getRemoteService(final ServletConfig config) throws ServletException {
	String serviceClassName = config.getInitParameter(REMOTE_SERVICE);
	if (serviceClassName == null) {
	    throw new ServletException(REMOTE_SERVICE + " not defined for this servlet!");
	}
	try {
	    return (Class<? extends RemoteService>) Class.forName(serviceClassName);
	} catch (ClassNotFoundException e) {
	    throw new ServletException(e);
	}
    }

    private static Module createModule(final ServletContext ctx) throws ServletException {
	String routerModuleClassName = ctx.getInitParameter(Module.class.getName());
	if (routerModuleClassName == null) {
	    throw new ServletException(Module.class.getName() + " not defined in web.xml!");
	}
	try {
	    Class<?> type = Class.forName(routerModuleClassName);
	    return (Module) type.newInstance();
	} catch (Exception e) {
	    throw new ServletException(e);
	}
    }

}
