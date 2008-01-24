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

package org.ourproject.kune.app.server;

import org.ourproject.kune.blogs.server.BlogServerModule;
import org.ourproject.kune.chat.server.ChatServerModule;
import org.ourproject.kune.docs.server.DocumentServerModule;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.server.LoggerMethodInterceptor;
import org.ourproject.kune.platf.server.PlatformServerModule;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;
import org.ourproject.kune.platf.server.rest.ContentJSONService;
import org.ourproject.kune.platf.server.rest.GroupJSONService;
import org.ourproject.kune.platf.server.rest.I18nTranslationJSONService;
import org.ourproject.kune.platf.server.rest.TestJSONService;
import org.ourproject.kune.platf.server.rest.UserJSONService;
import org.ourproject.kune.rack.RackBuilder;
import org.ourproject.kune.rack.RackModule;
import org.ourproject.kune.rack.filters.ForwardFilter;
import org.ourproject.kune.rack.filters.ListenerFilter;
import org.ourproject.kune.rack.filters.LogFilter;
import org.ourproject.kune.rack.filters.RedirectFilter;
import org.ourproject.kune.rack.filters.rest.RESTServicesModule;
import org.ourproject.kune.sitebar.client.rpc.UserService;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Scope;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.SessionScoped;
import com.wideplay.warp.jpa.JpaUnit;

public class KuneRackModule implements RackModule {

    private final Module configModule;

    public KuneRackModule(final String jpaUnit, final String propertiesFileName, final Scope sessionScope) {
        configModule = new AbstractModule() {
            public void configure() {
                bindInterceptor(Matchers.any(), new NotInObject(), new LoggerMethodInterceptor());
                bindConstant().annotatedWith(JpaUnit.class).to(jpaUnit);
                bindConstant().annotatedWith(PropertiesFileName.class).to(propertiesFileName);
                if (sessionScope != null) {
                    bindScope(SessionScoped.class, sessionScope);
                }
            }
        };
    }

    public KuneRackModule() {
        this("development", "kune.properties", null);
    }

    public void configure(final RackBuilder builder) {
        installGuiceModules(builder);

        builder.add(KuneContainerListener.class);

        builder.exclude("/http-bind.*");
        builder.at(".*").install(new LogFilter());
        builder.at(".*").install(new GuiceFilter());

        builder.at("^/$").install(new RedirectFilter("/kune/"));
        builder.at("^/kune$").install(new RedirectFilter("/kune/"));

        builder.at("^/kune/$").install(new ListenerFilter(KuneApplicationListener.class),
                new ForwardFilter("/gwt/org.ourproject.kune.app.Kune/Kune.html"));

        builder.installGWTServices("^/kune/", SiteService.class, GroupService.class, ContentService.class,
                UserService.class, SocialNetworkService.class, I18nService.class);
        builder.installRESTServices("^/kune/json/", TestJSONService.class, GroupJSONService.class,
                UserJSONService.class, I18nTranslationJSONService.class, ContentJSONService.class);

        builder.at("^/kune/(.*)$").install(new ForwardFilter("^/kune/(.*)$", "/gwt/org.ourproject.kune.app.Kune/{0}"));
    }

	private void installGuiceModules(final RackBuilder builder) {
		builder.use(new ServletModule());
        builder.use(new PlatformServerModule());
        builder.use(new DocumentServerModule());
        builder.use(new ChatServerModule());
        builder.use(new BlogServerModule());
        builder.use(new RESTServicesModule());
        builder.use(configModule);
	}

}
