package cc.kune.core.server.xmpp;

import java.util.Collection;

/**
 * The Interface XmppRosterProvider should allow to get users's roster
 * information from different providers (initially openfire)
 */
public interface XmppRosterProvider {

  /**
   * Count.
   * 
   * @return the total number of items (only for test)
   */
  Long count();

  Collection<RosterItem> getRoster(String user);
}
