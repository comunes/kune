/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
package org.ourproject.kune.app.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.blogs.server.BlogServerModule;
import org.ourproject.kune.chat.server.ChatServerModule;
import org.ourproject.kune.docs.server.DocumentServerModule;
import org.ourproject.kune.gallery.server.GalleryServerModule;
import org.ourproject.kune.platf.server.LoggerMethodInterceptor;
import org.ourproject.kune.platf.server.PlatformServerModule;
import org.ourproject.kune.platf.server.init.FinderRegistry;
import org.ourproject.kune.platf.server.manager.file.EntityLogoDownloadManager;
import org.ourproject.kune.platf.server.manager.file.EntityLogoUploadManager;
import org.ourproject.kune.platf.server.manager.file.FileDownloadManager;
import org.ourproject.kune.platf.server.manager.file.FileUploadManager;
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
import org.ourproject.kune.wiki.server.WikiServerModule;

import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.client.rpcservices.I18nService;
import cc.kune.core.client.rpcservices.SiteService;
import cc.kune.core.client.rpcservices.SocialNetworkService;
import cc.kune.core.client.rpcservices.UserService;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Scope;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.SessionScoped;

public class KuneRackModule implements RackModule {
    public static final Log LOG = LogFactory.getLog(KuneRackModule.class);
    private final Module configModule;

    public KuneRackModule() {
        this("development", "kune.properties", null);
    }

    public KuneRackModule(final String jpaUnit, final String propertiesFileName, final Scope sessionScope) {

        configModule = new AbstractModule() {
            @Override
            public void configure() {
                install(FinderRegistry.init(new JpaPersistModule(jpaUnit)));
                bindInterceptor(Matchers.any(), new NotInObject(), new LoggerMethodInterceptor());
                bindConstant().annotatedWith(PropertiesFileName.class).to(propertiesFileName);
                if (sessionScope != null) {
                    bindScope(SessionScoped.class, sessionScope);
                }
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(final RackBuilder builder) {
        installGuiceModules(builder);

        builder.add(KuneContainerListener.class);

        builder.exclude("/http-bind.*");
        builder.exclude("/public/.*");
        builder.exclude("/images/.*");
        builder.exclude("/stylesheets/.*");
        builder.exclude("/javascripts/.*");
        builder.exclude("/templates/.*");

        /* Wave with context '/' */
        builder.exclude("/");
        builder.exclude("/attachment/.*");
        builder.exclude("/auth/signin");
        builder.exclude("/auth/signout");
        builder.exclude("/auth/register");
        builder.exclude("/fetch/.*");
        builder.exclude("/robot/dataapi");
        builder.exclude("/robot/dataapi/oauth/.*");
        builder.exclude("/robot/dataapi/rpc");
        builder.exclude("/robot/register/.*");
        builder.exclude("/robot/rpc");
        builder.exclude("/gadgets");
        builder.exclude("/gadgets/.*");
        builder.exclude("/socket.io/*");
        builder.exclude("/socket.io/.*]");
        builder.exclude("/socket");
        builder.exclude("/static/.*");
        builder.exclude("/webclient/.*");

        builder.at(".*").install(new LogFilter());
        builder.at(".*").install(new GuiceFilter());

        // NOTE: Commented this while testing Wave
        // builder.at("^/$").install(new RedirectFilter("/ws/"));
        builder.at("^/ws").install(new RedirectFilter("/ws/"));

        builder.at("^/ws/$").install(new ListenerFilter(KuneApplicationListener.class),
                new ForwardFilter("/ws/ws.html"));

        builder.installGWTServices("^/ws/", SiteService.class, GroupService.class, ContentService.class,
                UserService.class, SocialNetworkService.class, I18nService.class);
        builder.installRESTServices("^/ws/json/", TestJSONService.class, GroupJSONService.class, UserJSONService.class,
                I18nTranslationJSONService.class, ContentJSONService.class);
        builder.installServlet("^/ws/servlets/", FileUploadManager.class, FileDownloadManager.class,
                EntityLogoUploadManager.class, EntityLogoDownloadManager.class);

        builder.at("^/ws/(.*)$").install(new ForwardFilter("^/ws/(.*)$", "/ws/{0}"));
    }

    private void installGuiceModules(final RackBuilder builder) {
        // https://code.google.com/p/google-guice/wiki/ServletModule
        builder.use(new ServletModule());
        builder.use(new PlatformServerModule());
        builder.use(new DocumentServerModule());
        builder.use(new BlogServerModule());
        builder.use(new WikiServerModule());
        builder.use(new ChatServerModule());
        builder.use(new GalleryServerModule());
        builder.use(new RESTServicesModule());
        builder.use(configModule);
    }

}
