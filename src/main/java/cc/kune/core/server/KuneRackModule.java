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
package cc.kune.core.server;

import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.barters.server.BarterServerModule;
import cc.kune.blogs.server.BlogServerModule;
import cc.kune.chat.server.ChatServerModule;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.client.rpcservices.I18nService;
import cc.kune.core.client.rpcservices.SiteService;
import cc.kune.core.client.rpcservices.SocialNetworkService;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.server.init.FinderRegistry;
import cc.kune.core.server.manager.file.EntityBackgroundDownloadManager;
import cc.kune.core.server.manager.file.EntityBackgroundUploadManager;
import cc.kune.core.server.manager.file.EntityLogoDownloadManager;
import cc.kune.core.server.manager.file.EntityLogoUploadManager;
import cc.kune.core.server.manager.file.FileDownloadManager;
import cc.kune.core.server.manager.file.FileGwtUploadServlet;
import cc.kune.core.server.manager.file.FileUploadManager;
import cc.kune.core.server.manager.file.UserLogoDownloadManager;
import cc.kune.core.server.properties.PropertiesFileName;
import cc.kune.core.server.rack.RackBuilder;
import cc.kune.core.server.rack.RackModule;
import cc.kune.core.server.rack.filters.ForwardFilter;
import cc.kune.core.server.rack.filters.ListenerFilter;
import cc.kune.core.server.rack.filters.LogFilter;
import cc.kune.core.server.rack.filters.RedirectFilter;
import cc.kune.core.server.rack.filters.rest.RESTServicesModule;
import cc.kune.core.server.rest.ContentJSONService;
import cc.kune.core.server.rest.GroupJSONService;
import cc.kune.core.server.rest.I18nTranslationJSONService;
import cc.kune.core.server.rest.TestJSONService;
import cc.kune.core.server.rest.UserJSONService;
import cc.kune.docs.server.DocumentServerModule;
import cc.kune.events.server.EventsServerModule;
import cc.kune.lists.client.rpc.ListsService;
import cc.kune.lists.server.ListsServerModule;
import cc.kune.tasks.server.TaskServerModule;
import cc.kune.wiki.server.WikiServerModule;

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
    this("development", null);
  }

  public KuneRackModule(final String jpaUnit, final Scope sessionScope) {

    final SystemConfiguration sysConf = new SystemConfiguration();
    final String kuneConfig = sysConf.getString("kune.server.config");

    configModule = new AbstractModule() {
      @Override
      public void configure() {
        install(FinderRegistry.init(new JpaPersistModule(jpaUnit)));
        bindInterceptor(Matchers.annotatedWith(LogThis.class), new NotInObject(),
            new LoggerMethodInterceptor());
        bindConstant().annotatedWith(PropertiesFileName.class).to(kuneConfig);
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
        UserService.class, SocialNetworkService.class, I18nService.class, ListsService.class);
    builder.installRESTServices("^/ws/json/", TestJSONService.class, GroupJSONService.class,
        UserJSONService.class, I18nTranslationJSONService.class, ContentJSONService.class);
    builder.installServlet("^/ws/servlets/", FileUploadManager.class, FileDownloadManager.class,
        EntityLogoUploadManager.class, EntityLogoDownloadManager.class, FileGwtUploadServlet.class,
        EntityBackgroundDownloadManager.class, EntityBackgroundUploadManager.class,
        UserLogoDownloadManager.class);

    builder.at("^/ws/(.*)$").install(new ForwardFilter("^/ws/(.*)$", "/ws/{0}"));
  }

  private void installGuiceModules(final RackBuilder builder) {
    // https://code.google.com/p/google-guice/wiki/ServletModule
    builder.use(new ServletModule() {
      @Override
      protected void configureServlets() {
        filter("/*").through(CustomPersistFilter.class);
        super.configureServlets();
      }
    });
    builder.use(new PlatformServerModule());
    builder.use(new DocumentServerModule());
    builder.use(new BlogServerModule());
    builder.use(new WikiServerModule());
    builder.use(new ChatServerModule());
    builder.use(new BarterServerModule());
    builder.use(new EventsServerModule());
    builder.use(new TaskServerModule());
    builder.use(new ListsServerModule());
    // builder.use(new GalleryServerModule());
    builder.use(new RESTServicesModule());
    builder.use(configModule);
  }

}
