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

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.barters.server.BarterServerModule;
import cc.kune.blogs.server.BlogServerModule;
import cc.kune.chat.server.ChatServerModule;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.client.rpcservices.I18nService;
import cc.kune.core.client.rpcservices.SiteService;
import cc.kune.core.client.rpcservices.SocialNetService;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.server.manager.file.EntityBackgroundDownloadManager;
import cc.kune.core.server.manager.file.EntityBackgroundUploadManager;
import cc.kune.core.server.manager.file.EntityLogoDownloadManager;
import cc.kune.core.server.manager.file.EntityLogoUploadManager;
import cc.kune.core.server.manager.file.FileDownloadManager;
import cc.kune.core.server.manager.file.FileGwtUploadServlet;
import cc.kune.core.server.manager.file.FileUploadManager;
import cc.kune.core.server.manager.file.UserLogoDownloadManager;
import cc.kune.core.server.manager.impl.GroupServerUtils;
import cc.kune.core.server.persist.DataSourceKunePersistModule;
import cc.kune.core.server.persist.DataSourceOpenfirePersistModule;
import cc.kune.core.server.persist.KuneTransactional;
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
import cc.kune.core.server.scheduler.CronServerTasksManager;
import cc.kune.docs.server.DocumentServerModule;
import cc.kune.events.server.EventsServerModule;
import cc.kune.events.server.EventsServlet;
import cc.kune.events.server.utils.EventsServerConversionUtil;
import cc.kune.hspace.client.ClientStatsService;
import cc.kune.lists.client.rpc.ListsService;
import cc.kune.lists.server.ListsServerModule;
import cc.kune.tasks.server.TaskServerModule;
import cc.kune.wave.server.KuneWaveServerUtils;
import cc.kune.wave.server.kspecific.WaveEmailNotifier;
import cc.kune.wiki.server.WikiServerModule;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Scope;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.SessionScoped;

public class KuneRackModule implements RackModule {

  public static final Log LOG = LogFactory.getLog(KuneRackModule.class);
  private final Module configModule;
  private final String suffix;

  public KuneRackModule() {
    this(null, "/ws", null);
  }

  private KuneRackModule(final String settedJpaUnit, final String suffix, final Scope sessionScope) {
    this.suffix = suffix;

    configModule = new AbstractModule() {
      @Override
      public void configure() {
        bindInterceptor(Matchers.annotatedWith(LogThis.class), new NotInObject(),
            new LoggerMethodInterceptor());
        if (sessionScope != null) {
          bindScope(SessionScoped.class, sessionScope);
        }
        // This can be used also in Gin:
        // http://code.google.com/p/google-gin/issues/detail?id=60
        requestStaticInjection(KuneWaveServerUtils.class);
        requestStaticInjection(EventsServerConversionUtil.class);
        requestStaticInjection(GroupServerUtils.class);
      }
    };
  }

  @Override
  @SuppressWarnings("unchecked")
  public void configure(final RackBuilder builder) {
    installGuiceModules(builder);

    builder.add(KuneContainerListener.class);
    builder.add(WaveEmailNotifier.class);
    builder.add(CronServerTasksManager.class);

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
    // builder.at("^/$").install(new RedirectFilter(KUNE_PREFIX + "/"));
    builder.at("^" + suffix + "").install(new RedirectFilter(suffix + "/"));

    builder.at("^" + suffix + "/$").install(new ListenerFilter(KuneApplicationListener.class),
        new ForwardFilter(suffix + "/ws.html"));

    builder.installGWTServices("^" + suffix + "/", SiteService.class, GroupService.class,
        ContentService.class, UserService.class, SocialNetService.class, I18nService.class,
        ListsService.class, ClientStatsService.class);
    builder.installRESTServices("^" + suffix + "/json/", TestJSONService.class, GroupJSONService.class,
        UserJSONService.class, I18nTranslationJSONService.class, ContentJSONService.class);
    builder.installServlet("^" + suffix + "/servlets/", FileUploadManager.class,
        FileDownloadManager.class, EntityLogoUploadManager.class, EntityLogoDownloadManager.class,
        FileGwtUploadServlet.class, EntityBackgroundDownloadManager.class,
        EntityBackgroundUploadManager.class, UserLogoDownloadManager.class, EventsServlet.class);

    builder.at("^" + suffix + "/(.*)$").install(
        new ForwardFilter("^" + suffix + "/(.*)$", suffix + "/{0}"));
  }

  private void installGuiceModules(final RackBuilder builder) {
    // https://code.google.com/p/google-guice/wiki/ServletModule
    builder.use(new ServletModule() {
      @Override
      protected void configureServlets() {
        final DataSourceKunePersistModule kuneDataSource = new DataSourceKunePersistModule();
        install(kuneDataSource);
        install(new DataSourceOpenfirePersistModule(kuneDataSource.getKuneProperties()));
        bindInterceptor(annotatedWith(KuneTransactional.class), any(),
            kuneDataSource.getTransactionInterceptor());
        bindInterceptor(any(), annotatedWith(KuneTransactional.class),
            kuneDataSource.getTransactionInterceptor());

        // more bindings

        filter("/*").through(DataSourceKunePersistModule.MY_DATA_SOURCE_ONE_FILTER_KEY);
        filter("/*").through(DataSourceOpenfirePersistModule.MY_DATA_SOURCE_TWO_FILTER_KEY);
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
