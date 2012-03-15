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
      dbProperties.setProperty("hibernate.connection.url",
          kuneProperties.get(KuneProperties.SITE_DB_URL));
      dbProperties.setProperty("hibernate.connection.username",
          kuneProperties.get(KuneProperties.SITE_DB_USER));
      dbProperties.setProperty("hibernate.connection.password",
          kuneProperties.get(KuneProperties.SITE_DB_PASSWORD));
      jpm.properties(dbProperties);
    }

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