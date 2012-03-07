package cc.kune.core.server.persist;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DataSourceKuneSessionProvider implements Provider<Session> {
  /** The entity manger to retrieve the session from. */
  @Inject
  @DataSourceKune
  private Provider<EntityManager> entityManagerProvider;

  /**
   * @return the Hibernate session, being the delegate of the entity manager
   *         provided by the injected entity manager provider.
   */
  @Override
  public Session get() {
    final Session session = (Session) entityManagerProvider.get().getDelegate();
    // configure session i.e. flush mode or filtering
    return session;
  }
};