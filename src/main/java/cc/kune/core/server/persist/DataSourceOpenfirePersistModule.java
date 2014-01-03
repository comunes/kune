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
package cc.kune.core.server.persist;

import java.util.Properties;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.xmpp.OpenfireXmppRosterFinder;
import cc.kune.core.server.xmpp.OpenfireXmppRosterPresenceFinder;
import cc.kune.core.server.xmpp.OpenfireXmppRosterPresenceProvider;
import cc.kune.core.server.xmpp.OpenfireXmppRosterProvider;
import cc.kune.core.server.xmpp.XmppRosterPresenceProvider;
import cc.kune.core.server.xmpp.XmppRosterPresenceProviderMock;
import cc.kune.core.server.xmpp.XmppRosterProvider;
import cc.kune.core.server.xmpp.XmppRosterProviderMock;

import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.persist.jpa.OpenfireJpaLocalTxnInterceptor;

// TODO: Auto-generated Javadoc
/**
 * The Class DataSourceOpenfirePersistModule.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DataSourceOpenfirePersistModule extends PrivateModule {

  /** The Constant MY_DATA_SOURCE_TWO_FILTER_KEY. */
  public static final Key<CustomPersistFilter> MY_DATA_SOURCE_TWO_FILTER_KEY = Key.get(
      CustomPersistFilter.class, DataSourceOpenfire.class);

  /** The kune properties. */
  private final KuneProperties kuneProperties;

  /** The transaction interceptor. */
  private OpenfireJpaLocalTxnInterceptor transactionInterceptor;

  /**
   * Instantiates a new data source openfire persist module.
   * 
   * @param kuneProperties
   *          the kune properties
   */
  public DataSourceOpenfirePersistModule(final KuneProperties kuneProperties) {
    this.kuneProperties = kuneProperties;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.inject.PrivateModule#configure()
   */
  @Override
  public void configure() {
    if (kuneProperties.getBoolean(KuneProperties.SITE_OPENFIRE_IGNORE)) {
      bind(XmppRosterProvider.class).to(XmppRosterProviderMock.class).in(Singleton.class);
      expose(XmppRosterProvider.class);
      bind(XmppRosterPresenceProvider.class).to(XmppRosterPresenceProviderMock.class).in(Singleton.class);
      expose(XmppRosterPresenceProvider.class);
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
      jpm.addFinder(OpenfireXmppRosterPresenceFinder.class);
      jpm.addFinder(OpenfireXmppRosterFinder.class);
      install(jpm);

      bind(XmppRosterProvider.class).to(OpenfireXmppRosterProvider.class).in(Singleton.class);
      bind(XmppRosterPresenceProvider.class).to(OpenfireXmppRosterPresenceProvider.class).in(
          Singleton.class);
      expose(XmppRosterProvider.class);
      expose(XmppRosterPresenceProvider.class);

      bind(Session.class).annotatedWith(DataSourceOpenfire.class).toProvider(
          DataSourceOpenfireSessionProvider.class);
      expose(Session.class).annotatedWith(DataSourceOpenfire.class);

      final Provider<EntityManager> entityManagerProvider = binder().getProvider(EntityManager.class);
      bind(EntityManager.class).annotatedWith(DataSourceOpenfire.class).toProvider(entityManagerProvider);
      expose(EntityManager.class).annotatedWith(DataSourceOpenfire.class);

      transactionInterceptor = new OpenfireJpaLocalTxnInterceptor();
      requestInjection(transactionInterceptor);

      expose(OpenfireXmppRosterPresenceFinder.class);
      expose(OpenfireXmppRosterFinder.class);

      bind(MY_DATA_SOURCE_TWO_FILTER_KEY).to(CustomPersistFilter.class);
      expose(MY_DATA_SOURCE_TWO_FILTER_KEY);

      bind(GenericPersistenceInitializer.class).asEagerSingleton();
    }
  }

  /**
   * Gets the transaction interceptor.
   * 
   * @return the transaction interceptor
   */
  public OpenfireJpaLocalTxnInterceptor getTransactionInterceptor() {
    return transactionInterceptor;
  }

}
