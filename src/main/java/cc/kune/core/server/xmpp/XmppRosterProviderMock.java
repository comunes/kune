package cc.kune.core.server.xmpp;

import java.util.Collection;
import java.util.Collections;

public class XmppRosterProviderMock implements XmppRosterProvider {

  @Override
  public Long count() {
    return 0l;
  }

  @Override
  public Collection<RosterItem> getRoster(final String user) {
    return Collections.emptyList();
  }

}
