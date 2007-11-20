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

package org.ourproject.kune.app.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ourproject.kune.app.server.servlet.Application;
import org.ourproject.kune.app.server.servlet.ApplicationBuilder;
import org.ourproject.kune.app.server.servlet.ApplicationListener;
import org.ourproject.kune.chat.server.ChatServerModule;
import org.ourproject.kune.docs.server.DocumentServerModule;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.server.LoggerMethodInterceptor;
import org.ourproject.kune.platf.server.PlatformServerModule;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;
import org.ourproject.kune.sitebar.client.rpc.UserService;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Scope;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public class KuneApp {
    private final String jpaUnit;
    private final String propertiesFileName;
    private final Scope sessionScope;

    public KuneApp(final String jpaUnit, final String propertiesFileName, final Scope sessionScope) {
        this.jpaUnit = jpaUnit;
        this.propertiesFileName = propertiesFileName;
        this.sessionScope = sessionScope;
    }

    public void configure(final ApplicationBuilder builder) {
        builder.use(new PlatformServerModule());
        builder.use(new DocumentServerModule());
        builder.use(new ChatServerModule());
        builder.use(new AbstractModule() {
            public void configure() {
                bindInterceptor(Matchers.any(), new NotInObject(), new LoggerMethodInterceptor());
                bindScope(SessionScoped.class, sessionScope);
                bindConstant().annotatedWith(JpaUnit.class).to(jpaUnit);
                bindConstant().annotatedWith(PropertiesFileName.class).to(propertiesFileName);
            }
        });

        Application app = builder.create("kune", "Kune.html", "gwt/org.ourproject.kune.app.Kune");
        app.useService("SiteService", SiteService.class);
        app.useService("GroupService", GroupService.class);
        app.useService("ContentService", ContentService.class);
        app.useService("UserService", UserService.class);
        app.useService("SocialNetworkService", SocialNetworkService.class);
        app.with(KuneApplicationListener.class);
        app.add(new KuneLifeCycleListener());
    }

    public static class KuneApplicationListener implements ApplicationListener {
        @Inject
        UserSession userSession;

        public void onApplicationStart(final HttpServletRequest request, final HttpServletResponse response) {
            // Comment this: (only setHash where user isLogged)
            // Also we need the sessionId when the client application is already
            // running (for instance if we restart the server)

            String userSessionId = request.getSession().getId();
            userSession.setHash(userSessionId);
        }
    }
}
