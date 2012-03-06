package cc.kune.core.server.xmpp;

import com.google.inject.persist.finder.Finder;

public interface OpenfireXmppRosterFinder {

  @Finder(query = "SELECT count(*) FROM Roster r")
  public Long count();

}
