/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.persist;

import java.util.Properties;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.xmpp.OpenfireXmppRosterFinder;
import cc.kune.core.server.xmpp.OpenfireXmppRosterProvider;
import cc.kune.core.server.xmpp.XmppRosterProvider;
import cc.kune.core.server.xmpp.XmppRosterProviderMock;

import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.persist.jpa.OpenfireJpaLocalTxnInterceptor;

public class DataSourceOpenfirePersistModule extends PrivateModule {
  public static final Key<CustomPersistFilter> MY_DATA_SOURCE_TWO_FILTER_KEY = Key.get(
      CustomPersistFilter.class, DataSourceOpenfire.class);
  private final KuneProperties kuneProperties;
  private OpenfireJpaLocalTxnInterceptor transactionInterceptor;

  public DataSourceOpenfirePersistModule(final KuneProperties kuneProperties) {
    this.kuneProperties = kuneProperties;
  }

  @Override
  public void configure() {
    if (kuneProperties.getBoolean(KuneProperties.SITE_OPENFIRE_IGNORE)) {
      bind(XmppRosterProvider.class).to(XmppRosterProviderMock.class).in(Singleton.class);
      expose(XmppRosterProvider.class);
    } else {
      final JpaPersistModule jpm = new JpaPersistModule("openfire");

      final Properties dbProperties = new Properties();

      dbProperties.setProperty("hibernate.connection.url",
          kuneProperties.get(KuneProperties.SITE_OPENFIRE_DB_URL));
      dbProperties.setProperty("hibernate.connection.username",
          kuneProperties.get(KuneProperties.SITE_OPENFIRE_DB_USER));
      dbProperties.setProperty("hibernate.connection.password",
          kuneProperties.get(KuneProperties.SITE_OPENFIRE_DB_PASSWORD));
      dbProperties.setProperty("exclude-unlisted-classes", "true");

      jpm.properties(dbProperties);
      install(jpm.addFinder(OpenfireXmppRosterFinder.class));
      expose(OpenfireXmppRosterFinder.class);

      bind(XmppRosterProvider.class).to(OpenfireXmppRosterProvider.class).in(Singleton.class);
      expose(XmppRosterProvider.class);

      bind(Session.class).annotatedWith(DataSourceOpenfire.class).toProvider(
          DataSourceOpenfireSessionProvider.class);
      expose(Session.class).annotatedWith(DataSourceOpenfire.class);

      final Provider<EntityManager> entityManagerProvider = binder().getProvider(EntityManager.class);
      bind(EntityManager.class).annotatedWith(DataSourceOpenfire.class).toProvider(entityManagerProvider);
      expose(EntityManager.class).annotatedWith(DataSourceOpenfire.class);

      transactionInterceptor = new OpenfireJpaLocalTxnInterceptor();
      requestInjection(transactionInterceptor);

      bind(MY_DATA_SOURCE_TWO_FILTER_KEY).to(CustomPersistFilter.class);
      expose(MY_DATA_SOURCE_TWO_FILTER_KEY);

      bind(GenericPersistenceInitializer.class).asEagerSingleton();
    }
  }

  public OpenfireJpaLocalTxnInterceptor getTransactionInterceptor() {
    return transactionInterceptor;
  }

}
