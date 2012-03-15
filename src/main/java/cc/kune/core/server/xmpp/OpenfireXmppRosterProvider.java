package cc.kune.core.server.xmpp;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.manager.impl.DefaultManager;
import cc.kune.core.server.persist.DataSourceOpenfire;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class OpenfireXmppRosterProvider extends DefaultManager<RosterItem, Long> implements
    XmppRosterProvider {
  private static final Log LOG = LogFactory.getLog(OpenfireXmppRosterProvider.class);
  private final OpenfireXmppRosterFinder finder;

  @Inject
  public OpenfireXmppRosterProvider(@DataSourceOpenfire final Provider<EntityManager> em,
      final OpenfireXmppRosterFinder finder) {
    super(em, RosterItem.class);
    this.finder = finder;
  }

  @Override
  public Long count() {
    return finder.count();
  }

  @Override
  public Collection<RosterItem> getRoster(final String user) {
    return finder.get(user);
  }

}
