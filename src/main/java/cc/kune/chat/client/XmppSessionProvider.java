package cc.kune.chat.client;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.suco.client.Suco;
import com.google.inject.Provider;

public class XmppSessionProvider implements Provider<XmppSession> {

  @Override
  public XmppSession get() {
    return Suco.get(XmppSession.class);
  }

}
