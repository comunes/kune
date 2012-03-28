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
package cc.kune.core.server.persist;

import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.properties.KunePropertiesDefault;
import cc.kune.domain.finders.ContainerFinder;
import cc.kune.domain.finders.ContentFinder;
import cc.kune.domain.finders.ExtMediaDescripFinder;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.I18nCountryFinder;
import cc.kune.domain.finders.I18nLanguageFinder;
import cc.kune.domain.finders.I18nTranslationFinder;
import cc.kune.domain.finders.LicenseFinder;
import cc.kune.domain.finders.RateFinder;
import cc.kune.domain.finders.TagFinder;
import cc.kune.domain.finders.TagUserContentFinder;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.persist.jpa.KuneJpaLocalTxnInterceptor;

public class DataSourceKunePersistModule extends PrivateModule {
  // FIXME Trying to make this PrivateModule so we can have two Persist sources
  // http://code.google.com/p/google-guice/wiki/GuicePersistMultiModules

  public static final Log LOG = LogFactory.getLog(DataSourceKunePersistModule.class);
  public static final Key<CustomPersistFilter> MY_DATA_SOURCE_ONE_FILTER_KEY = Key.get(
      CustomPersistFilter.class, DataSourceKune.class);
  private String kuneConfig;
  private KunePropertiesDefault kuneProperties;
  private String settedJpaUnit = null;
  private KuneJpaLocalTxnInterceptor transactionInterceptor;

  /**
   * Instantiates this module (main constructor)
   */
  public DataSourceKunePersistModule() {
    init(null);
  }

  /**
   * Instantiates this module only during tests
   * 
   * @param settedProperties
   *          the setted properties
   * @param settedJpaUnit
   *          the setted jpa unit
   */
  public DataSourceKunePersistModule(final String settedProperties, final String settedJpaUnit) {
    this.settedJpaUnit = settedJpaUnit;
    init(settedProperties);
  }

  @Override
  public void configure() {

    bind(KuneProperties.class).toInstance(kuneProperties);

    // precedence method param > properties
    final String configuredJpaUnit = kuneProperties.get(KuneProperties.SITE_DB_PERSISTENCE_NAME);
    final String jpaUnit = settedJpaUnit != null ? settedJpaUnit
        : configuredJpaUnit != null ? configuredJpaUnit : "development";
    LOG.info(String.format("Using persistence unit '%s' and properties '%s'", jpaUnit, kuneConfig));

    final JpaPersistModule jpm = new JpaPersistModule(jpaUnit);

    if (!jpaUnit.equals("test")) {
      // In tests (and development) we don't override this db info)
      final Properties dbProperties = new Properties();
      final String dbUrl = kuneProperties.get(KuneProperties.SITE_DB_URL);
      final String dbUser = kuneProperties.get(KuneProperties.SITE_DB_USER);
      final String dbPass = kuneProperties.get(KuneProperties.SITE_DB_PASSWORD);
      dbProperties.setProperty("hibernate.connection.url", dbUrl);
      dbProperties.setProperty("hibernate.connection.username", dbUser);
      dbProperties.setProperty("hibernate.connection.password", dbPass);
      jpm.properties(dbProperties);
      LOG.info(String.format("Using user '%s' and connection '%s'", dbUser, dbUrl));
      // LOG.debug(String.format("dbpass '%s'", dbPass));
    }

    // http://google-guice.googlecode.com/svn/trunk/javadoc/com/google/inject/Injector.html
    // (...) Just-in-time bindings created for child injectors will be
    // created in an ancestor injector whenever possible (...)
    // (This fails with finders, then we make explicit bindings)

    jpm.addFinder(ContainerFinder.class);
    jpm.addFinder(ContentFinder.class);
    jpm.addFinder(ExtMediaDescripFinder.class);
    jpm.addFinder(GroupFinder.class);
    jpm.addFinder(I18nCountryFinder.class);
    jpm.addFinder(I18nLanguageFinder.class);
    jpm.addFinder(I18nTranslationFinder.class);
    jpm.addFinder(LicenseFinder.class);
    jpm.addFinder(RateFinder.class);
    jpm.addFinder(TagFinder.class);
    jpm.addFinder(TagUserContentFinder.class);
    jpm.addFinder(UserFinder.class);
    install(jpm);

    bind(Session.class).annotatedWith(DataSourceKune.class).toProvider(
        DataSourceKuneSessionProvider.class);

    final Provider<EntityManager> entityManagerProvider = binder().getProvider(EntityManager.class);
    bind(EntityManager.class).annotatedWith(DataSourceKune.class).toProvider(entityManagerProvider);

    transactionInterceptor = new KuneJpaLocalTxnInterceptor();
    requestInjection(transactionInterceptor);

    bind(MY_DATA_SOURCE_ONE_FILTER_KEY).to(CustomPersistFilter.class);

    expose(EntityManager.class).annotatedWith(DataSourceKune.class);
    expose(Session.class).annotatedWith(DataSourceKune.class);
    expose(KuneProperties.class);
    expose(ContainerFinder.class);
    expose(ContentFinder.class);
    expose(ExtMediaDescripFinder.class);
    expose(GroupFinder.class);
    expose(I18nCountryFinder.class);
    expose(I18nLanguageFinder.class);
    expose(I18nTranslationFinder.class);
    expose(LicenseFinder.class);
    expose(RateFinder.class);
    expose(TagFinder.class);
    expose(TagUserContentFinder.class);
    expose(UserFinder.class);
    expose(MY_DATA_SOURCE_ONE_FILTER_KEY);

    bind(GenericPersistenceInitializer.class).asEagerSingleton();
  }

  public KuneProperties getKuneProperties() {
    return kuneProperties;
  }

  public KuneJpaLocalTxnInterceptor getTransactionInterceptor() {
    return transactionInterceptor;
  }

  private void init(final String settedProperties) {
    final SystemConfiguration sysConf = new SystemConfiguration();
    kuneConfig = settedProperties != null ? settedProperties : sysConf.getString("kune.server.config");
    kuneProperties = new KunePropertiesDefault(kuneConfig);
  }
}