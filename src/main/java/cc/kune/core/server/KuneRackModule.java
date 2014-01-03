/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import org.waveprotocol.box.server.authentication.SessionManager;
import org.waveprotocol.box.server.robots.dataapi.DataApiOAuthServlet;
import org.waveprotocol.box.server.rpc.AttachmentInfoServlet;
import org.waveprotocol.box.server.rpc.AttachmentServlet;

import cc.kune.barters.server.BarterServerModule;
import cc.kune.blogs.server.BlogServerModule;
import cc.kune.chat.server.ChatServerModule;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.client.rpcservices.I18nService;
import cc.kune.core.client.rpcservices.InvitationService;
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
import cc.kune.core.server.manager.impl.GroupServerUtils;
import cc.kune.core.server.notifier.UsersOnline;
import cc.kune.core.server.persist.DataSourceKunePersistModule;
import cc.kune.core.server.persist.DataSourceOpenfirePersistModule;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.persist.OpenfireTransactional;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.rack.RackBuilder;
import cc.kune.core.server.rack.RackModule;
import cc.kune.core.server.rack.filters.ForwardFilter;
import cc.kune.core.server.rack.filters.ListenerFilter;
import cc.kune.core.server.rack.filters.RedirectFilter;
import cc.kune.core.server.rack.filters.rest.RESTServicesModule;
import cc.kune.core.server.rest.ContentCORSService;
import cc.kune.core.server.rest.ContentJSONService;
import cc.kune.core.server.rest.GroupJSONService;
import cc.kune.core.server.rest.I18nTranslationJSONService;
import cc.kune.core.server.rest.SiteCORSService;
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
import cc.kune.trash.server.TrashServerModule;
import cc.kune.wave.server.kspecific.KuneWaveServerUtils;
import cc.kune.wave.server.kspecific.WaveEmailNotifier;
import cc.kune.wiki.server.WikiServerModule;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.jpa.KuneJpaLocalTxnInterceptor;
import com.google.inject.persist.jpa.OpenfireJpaLocalTxnInterceptor;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;

/**
 * The Class KuneRackModule.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneRackModule implements RackModule {

  private static final String EMBED_SUFFIX = "/wse";

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(KuneRackModule.class);

  /** The suffix of the kune url. */
  private static final String SUFFIX = "/ws";

  /**
   * The suffix regexp of the kune url, to match /ws/ and /wse/ (embed) for
   * instance.
   */
  private static final String SUFFIX_REG_EXP = "/ws[e]*";

  /** The config module. */
  private final Module configModule;

  /**
   * Instantiates a new kune rack module.
   * 
   */
  public KuneRackModule() {

    configModule = new AbstractModule() {
      @Override
      public void configure() {
        // Warning: parent instances (like Wave classes) are not intercepted.
        // See: http://code.google.com/p/google-guice/issues/detail?id=461
        bindInterceptor(Matchers.annotatedWith(LogThis.class), new NotInObject(),
            new LoggerMethodInterceptor());
        // if (sessionScope != null) {
        // bindScope(SessionScoped.class, sessionScope);
        // }
        bind(UserSessionManager.class).to(UserSessionManagerImpl.class).in(Singleton.class);
        bind(UsersOnline.class).to(UserSessionManagerImpl.class).asEagerSingleton();
        requestStaticInjection(KuneWaveServerUtils.class);
        requestStaticInjection(EventsServerConversionUtil.class);
        requestStaticInjection(GroupServerUtils.class);
      }
    };
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.rack.RackModule#configure(cc.kune.core.server.rack.
   * RackBuilder)
   */
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

    /*
     * Wave with context '/'. See {@link CustomServerMain}
     */
    builder.exclude("/");
    builder.exclude("/gadget/gadgetlist");
    builder.exclude("/attachment/.*");
    builder.exclude(AttachmentServlet.ATTACHMENT_URL + "/*");
    builder.exclude(AttachmentServlet.THUMBNAIL_URL + "/*");
    builder.exclude(AttachmentInfoServlet.ATTACHMENTS_INFO_URL);

    builder.exclude(SessionManager.SIGN_IN_URL);
    builder.exclude("/auth/signout");
    builder.exclude("/auth/register");
    builder.exclude("/locale/.*");
    builder.exclude("/fetch/.*");
    builder.exclude("/search/.*");
    builder.exclude("/notification/.*");
    builder.exclude("/robot/dataapi");
    builder.exclude(DataApiOAuthServlet.DATA_API_OAUTH_PATH + "/.*");
    builder.exclude("/robot/dataapi/rpc");
    builder.exclude("/robot/register/.*");
    builder.exclude("/robot/rpc");
    builder.exclude("/webclient/remote_logging");
    builder.exclude("/profile/.*");
    builder.exclude("/waveref/.*");

    builder.exclude("/gadgets");
    builder.exclude("/gadgets/.*");
    builder.exclude("/socket.io/*");
    builder.exclude("/socket.io/.*]");
    builder.exclude("/socket");
    builder.exclude("/static/.*");
    builder.exclude("/webclient/.*");

    // builder.at(".*").install(new LogFilter());
    builder.at(".*").install(new GuiceFilter());

    // NOTE: Commented this while testing Wave
    // builder.at("^/$").install(new RedirectFilter(KUNE_PREFIX + "/"));
    builder.at("^" + SUFFIX).install(new RedirectFilter(SUFFIX + "/"));
    builder.at("^" + EMBED_SUFFIX).install(new RedirectFilter(EMBED_SUFFIX + "/"));

    builder.at("^" + SUFFIX + "/$").install(new ListenerFilter(KuneApplicationListener.class),
        new ForwardFilter(SUFFIX + "/ws.html"));

    builder.at("^" + EMBED_SUFFIX + "/$").install(new ListenerFilter(KuneApplicationListener.class),
        new ForwardFilter(EMBED_SUFFIX + "/wse.html"));

    builder.installGWTServices("^" + SUFFIX_REG_EXP + "/", SiteService.class, GroupService.class,
        ContentService.class, UserService.class, SocialNetService.class, I18nService.class,
        ListsService.class, ClientStatsService.class, InvitationService.class);
    builder.installRESTServices("^" + SUFFIX_REG_EXP + "/json/", TestJSONService.class,
        GroupJSONService.class, UserJSONService.class, I18nTranslationJSONService.class,
        ContentJSONService.class);
    builder.installCORSServices("^" + SUFFIX_REG_EXP + "/cors/", SiteCORSService.class,
        ContentCORSService.class);
    builder.installServlet("^" + SUFFIX_REG_EXP + "/servlets/", FileUploadManager.class,
        FileDownloadManager.class, EntityLogoUploadManager.class, EntityLogoDownloadManager.class,
        FileGwtUploadServlet.class, EntityBackgroundDownloadManager.class,
        EntityBackgroundUploadManager.class, EventsServlet.class);

    builder.at("^" + SUFFIX + "/(.*)$").install(
        new ForwardFilter("^" + SUFFIX + "/(.*)$", SUFFIX + "/{0}"));
    builder.at("^" + EMBED_SUFFIX + "/(.*)$").install(
        new ForwardFilter("^" + EMBED_SUFFIX + "/(.*)$", EMBED_SUFFIX + "/{0}"));
  }

  /**
   * Install guice modules.
   * 
   * @param builder
   *          the builder
   */
  private void installGuiceModules(final RackBuilder builder) {
    // https://code.google.com/p/google-guice/wiki/ServletModule
    builder.use(new ServletModule() {
      @Override
      protected void configureServlets() {
        final DataSourceKunePersistModule kuneDataSource = new DataSourceKunePersistModule();
        install(kuneDataSource);
        final KuneProperties kuneProperties = kuneDataSource.getKuneProperties();
        final DataSourceOpenfirePersistModule openfireDataSource = new DataSourceOpenfirePersistModule(
            kuneProperties);
        install(openfireDataSource);
        final KuneJpaLocalTxnInterceptor kuneJpaTxnInterceptor = kuneDataSource.getTransactionInterceptor();
        // Warning: parent instances (like Wave classes) are not intercepted
        // See: http://code.google.com/p/google-guice/issues/detail?id=461
        bindInterceptor(annotatedWith(KuneTransactional.class), any(), kuneJpaTxnInterceptor);
        bindInterceptor(any(), annotatedWith(KuneTransactional.class), kuneJpaTxnInterceptor);
        filter("/*").through(DataSourceKunePersistModule.MY_DATA_SOURCE_ONE_FILTER_KEY);
        if (!kuneProperties.getBoolean(KuneProperties.SITE_OPENFIRE_IGNORE)) {
          final OpenfireJpaLocalTxnInterceptor openfireJpaTxnInterceptor = openfireDataSource.getTransactionInterceptor();
          bindInterceptor(annotatedWith(OpenfireTransactional.class), any(), openfireJpaTxnInterceptor);
          bindInterceptor(any(), annotatedWith(OpenfireTransactional.class), openfireJpaTxnInterceptor);
          filter("/*").through(DataSourceOpenfirePersistModule.MY_DATA_SOURCE_TWO_FILTER_KEY);
        }
        super.configureServlets();
      }
    });
    builder.use(new PlatformServerModule());
    builder.use(new DocumentServerModule());
    builder.use(new WikiServerModule());
    builder.use(new BlogServerModule());
    builder.use(new EventsServerModule());
    builder.use(new TaskServerModule());
    builder.use(new ListsServerModule());
    builder.use(new ChatServerModule());
    builder.use(new BarterServerModule());
    builder.use(new TrashServerModule());
    // builder.use(new GalleryServerModule());
    builder.use(new RESTServicesModule());
    builder.use(configModule);
  }
}
