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
package cc.kune.core.server.integration;

import static com.google.inject.matcher.Matchers.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.ConfigurationException;
import org.mockito.Mockito;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.SearchModule;
import org.waveprotocol.box.server.ServerModule;
import org.waveprotocol.box.server.persistence.PersistenceModule;
import org.waveprotocol.box.server.robots.ProfileFetcherModule;
import org.waveprotocol.box.server.robots.RobotApiModule;
import org.waveprotocol.box.server.waveserver.WaveServerException;
import org.waveprotocol.box.server.waveserver.WaveServerImpl;
import org.waveprotocol.wave.federation.noop.NoOpFederationModule;

import cc.kune.barters.server.BarterServerModule;
import cc.kune.chat.server.ChatServerModule;
import cc.kune.core.server.DummyUserSessionManager;
import cc.kune.core.server.PlatformServerModule;
import cc.kune.core.server.TestConstants;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.manager.impl.GroupServerUtils;
import cc.kune.core.server.notifier.UsersOnline;
import cc.kune.core.server.persist.DataSourceKunePersistModule;
import cc.kune.core.server.persist.DataSourceOpenfirePersistModule;
import cc.kune.core.server.persist.KunePersistenceService;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.docs.server.DocumentServerModule;
import cc.kune.events.server.EventsServerModule;
import cc.kune.events.server.utils.EventsServerConversionUtil;
import cc.kune.lists.server.ListsServerModule;
import cc.kune.tasks.server.TaskServerModule;
import cc.kune.trash.server.TrashServerModule;
import cc.kune.wave.server.CustomSettingsBinder;
import cc.kune.wave.server.kspecific.KuneWaveServerUtils;
import cc.kune.wiki.server.WikiServerModule;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;

// TODO: Auto-generated Javadoc
/**
 * The Class IntegrationTestHelper.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class IntegrationTestHelper {

  /**
   * Creates the injector.
   * 
   * @return the injector
   */
  public static Injector createInjector() {
    Injector injector;
    try {
      System.setProperty("java.security.auth.login.config", "src/main/resources/jaas.config");
      injector = Guice.createInjector(CustomSettingsBinder.bindSettings(
          TestConstants.WAVE_TEST_PROPFILE, CoreSettings.class));
      final PersistenceModule wavePersistModule = injector.getInstance(PersistenceModule.class);
      final NoOpFederationModule federationModule = injector.getInstance(NoOpFederationModule.class);
      final DataSourceKunePersistModule kuneDataSource = new DataSourceKunePersistModule(
          "kune-tests.properties", TestConstants.PERSISTENCE_UNIT);
      final Module searchModule = injector.getInstance(SearchModule.class);
      final Module profilesModule = injector.getInstance(ProfileFetcherModule.class);
      final Injector childInjector = injector.createChildInjector(
          wavePersistModule,
          searchModule,
          profilesModule,
          kuneDataSource,
          new DataSourceOpenfirePersistModule(kuneDataSource.getKuneProperties()),
          new AbstractModule() {
            @Override
            protected void configure() {
              bindScope(SessionScoped.class, Scopes.SINGLETON);
              bindScope(RequestScoped.class, Scopes.SINGLETON);
              bind(UsersOnline.class).to(DummyUserSessionManager.class).asEagerSingleton();
              bind(UserSessionManager.class).to(DummyUserSessionManager.class).asEagerSingleton();
              // Used by I18nRPC to detect user lang
              bind(HttpServletRequest.class).toInstance(Mockito.mock(HttpServletRequest.class));
              bindInterceptor(annotatedWith(KuneTransactional.class), any(),
                  kuneDataSource.getTransactionInterceptor());
              bindInterceptor(any(), annotatedWith(KuneTransactional.class),
                  kuneDataSource.getTransactionInterceptor());
              install(kuneDataSource);
              requestStaticInjection(KuneWaveServerUtils.class);
              requestStaticInjection(EventsServerConversionUtil.class);
              requestStaticInjection(GroupServerUtils.class);
            }
          }, new ListsServerModule(), new RobotApiModule(), new PlatformServerModule(),
          new DocumentServerModule(), new ChatServerModule(), new ServerModule(false, 1, 1, 1, 1, 1),
          federationModule, new WikiServerModule(), new TaskServerModule(), new BarterServerModule(),
          new EventsServerModule(), new TrashServerModule());
      try {
        childInjector.getInstance(WaveServerImpl.class).initialize();
      } catch (final WaveServerException e) {
        e.printStackTrace();
      }
      return childInjector;
    } catch (final ConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Instantiates a new integration test helper.
   * 
   * @param startPersistence
   *          the start persistence
   * @param tests
   *          the tests
   */
  public IntegrationTestHelper(final boolean startPersistence, final Object... tests) {
    final Injector injector = createInjector();
    if (startPersistence) {
      injector.getInstance(KunePersistenceService.class).start();
    }
    for (final Object test : tests) {
      injector.injectMembers(test);
    }
  }
}
