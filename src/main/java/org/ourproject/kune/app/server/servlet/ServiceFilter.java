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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.inject.Injector;

public class ServiceFilter extends AbstractProcessor {
    private static final Log log = LogFactory.getLog(ServiceFilter.class);
    private final Class<? extends RemoteService> serviceClass;
    private final DelegatedRemoteServlet servlet;
    private final String relative;

    public ServiceFilter(final String appName, final String serviceName,
            final Class<? extends RemoteService> serviceClass) {
        super(POST);
        this.serviceClass = serviceClass;
        this.relative = "/" + appName + "/" + serviceName;
        this.servlet = new DelegatedRemoteServlet();
    }

    @Override
    public void init(final ServletContext servletContext, final Injector injector) {
        super.init(servletContext, injector);
        servlet.setServletContext(servletContext);
    }

    @Override
    protected boolean process(final String relativeUrl, final HttpServletRequest request,
            final HttpServletResponse response) throws IOException, ServletException {

        if (relative.equals(relativeUrl)) {
            log.debug("SESSION: " + request.getSession().getId() + " CALLING THE SERVICE: " + relative);
            RemoteService service = super.getInjector().getInstance(serviceClass);
            servlet.setService(service);
            servlet.doPost(request, response);
            return true;
        }
        return false;
    }
}
