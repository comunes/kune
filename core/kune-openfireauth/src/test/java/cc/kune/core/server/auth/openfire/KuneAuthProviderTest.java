package cc.kune.core.server.auth.openfire;

import java.net.MalformedURLException;

import org.jivesoftware.openfire.auth.ConnectionException;
import org.jivesoftware.openfire.auth.InternalUnauthenticatedException;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.junit.Test;

public class KuneAuthProviderTest {

  @Test
  public void maintest() throws UnauthorizedException, ConnectionException,
      InternalUnauthenticatedException, MalformedURLException, ClassNotFoundException,
      IllegalAccessException, InstantiationException {
    final KuneAuthProvider auth = new KuneAuthProvider();
    auth.authenticate("admin", "easyeasy");
    // Test with a valid session hash also
    // auth.authenticate("admin", "guplzs35jxe06vxfi2h4xk7q");

    // auth.authenticate("vjrj", "6rzonw7n39p01td1p0rfujvu3");
    // auth.authenticate("admin", "x04c5575ycquu96k4kajldaq");
    // Only usersession!!!
    // auth.authenticate("vjrj", "1006oh5apswj81tl4vcsk1gqpj");
  }

  @Test(expected = UnauthorizedException.class)
  public void wrongFileSession() throws UnauthorizedException, ConnectionException,
      InternalUnauthenticatedException, ClassNotFoundException, IllegalAccessException,
      InstantiationException {
    final KuneAuthProvider auth = new KuneAuthProvider();
    auth.authenticate("admin", "6rzonw7n39p01td1p0rfujvu3a");
  }

  @Test(expected = UnauthorizedException.class)
  public void wrongPass() throws UnauthorizedException, ConnectionException,
      InternalUnauthenticatedException, ClassNotFoundException, IllegalAccessException,
      InstantiationException {
    final KuneAuthProvider auth = new KuneAuthProvider();
    auth.authenticate("admin", "easyeasy2");
  }

  @Test(expected = UnauthorizedException.class)
  public void wrongSession() throws UnauthorizedException, ConnectionException,
      InternalUnauthenticatedException, ClassNotFoundException, IllegalAccessException,
      InstantiationException {
    final KuneAuthProvider auth = new KuneAuthProvider();
    auth.authenticate("admin", "6rzonw7n39p01td1p0rfujvu3");
  }

}
