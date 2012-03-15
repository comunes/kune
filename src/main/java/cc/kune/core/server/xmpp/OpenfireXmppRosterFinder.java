package cc.kune.core.server.xmpp;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

public interface OpenfireXmppRosterFinder {

  @Finder(query = "SELECT count(*) FROM RosterItem r")
  Long count();

  /**
   * Gets the.
   * 
   * @param shortname
   *          the shortname of the user but without the @domain
   * @return the list
   */
  @Finder(query = "from RosterItem WHERE username = :username", returnAs = ArrayList.class)
  List<RosterItem> get(@Named("username") final String shortname);

}
