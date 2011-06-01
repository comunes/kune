package cc.kune.selenium.login;

import org.testng.annotations.Test;

import cc.kune.selenium.KuneSeleniumTest;

public class LoginSeleniumTests extends KuneSeleniumTest {

  @Test(dataProvider = "correctlogin", enabled = false)
  public void severalsSignInSingOut(final String user, final String passwd) {
    login.assertIsDisconnected();
    login.signIn(user, passwd);
    login.assertIsConnectedAs(user);
    login.logout();

    login.assertIsDisconnected();
    login.signIn(user, passwd);
    login.assertIsConnectedAs(user);
    login.logout();

    login.assertIsDisconnected();
  }

  @Test(dataProvider = "correctlogin")
  public void signIn(final String user, final String passwd) {
    login.assertIsDisconnected();
    login.signIn(user, passwd);
    login.assertIsConnectedAs(user);
  }

  @Test
  public void signInIncorrectPasswd() {
    login.assertIsDisconnected();
    login.signIn("nouser", "nopassword");
    login.assertIsDisconnected();
  }
}
