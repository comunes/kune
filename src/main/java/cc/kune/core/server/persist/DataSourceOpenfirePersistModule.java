package cc.kune.core.server.persist;

import java.util.Properties;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import cc.kune.core.server.xmpp.OpenfireXmppRosterFinder;
import cc.kune.core.server.xmpp.OpenfireXmppRosterProvider;
import cc.kune.core.server.xmpp.XmppRosterProvider;

import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;

public class DataSourceOpenfirePersistModule extends PrivateModule {
  public static final Key<CustomPersistFilter> MY_DATA_SOURCE_TWO_FILTER_KEY = Key.get(
      CustomPersistFilter.class, DataSourceOpenfire.class);

  @Override
  public void configure() {
    final JpaPersistModule jpm = new JpaPersistModule("openfire");
    // jpm.properties(new Properties());

    final Properties dbProperties = new Properties();
    dbProperties.setProperty(
        "hibernate.connection.url",
        "jdbc:mysql://localhost/openfire4?createDatabaseIfNotExist=false&amp;useUnicode=true&amp;characterEncoding=UTF-8");
    // kuneProperties.get(KuneProperties.SITE_DB_URL));
    dbProperties.setProperty("hibernate.connection.username", "openfire");
    // kuneProperties.get(KuneProperties.SITE_DB_USER));
    dbProperties.setProperty("hibernate.connection.password", "easyeasy");
    dbProperties.setProperty("exclude-unlisted-classes", "true");

    // kuneProperties.get(KuneProperties.SITE_DB_PASSWORD));

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

    bind(MY_DATA_SOURCE_TWO_FILTER_KEY).to(CustomPersistFilter.class);
    expose(MY_DATA_SOURCE_TWO_FILTER_KEY);

    bind(GenericPersistenceInitializer.class).asEagerSingleton();
  }
}