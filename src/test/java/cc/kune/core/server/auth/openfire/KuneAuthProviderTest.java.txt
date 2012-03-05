package cc.kune.core.server.auth.openfire;

import org.jivesoftware.openfire.auth.ConnectionException;
import org.jivesoftware.openfire.auth.InternalUnauthenticatedException;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.junit.Ignore;
import org.junit.Test;

public class KuneAuthProviderTest {

  @Test
  @Ignore
  public void maintest() throws UnauthorizedException, ConnectionException,
      InternalUnauthenticatedException {
    final KuneAuthProvider auth = new KuneAuthProvider();
    auth.authenticate("admin", "169le0ik2rma81xhj4560ul7fm");
  }
}
