package cc.kune.selenium;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Shared behaviour in selenium tests
 */
public abstract class KuneSeleniumTest extends KuneSeleniumDefaults {

  protected String getTempString() {
    final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    final String value = dateFormat.format(new Date());
    return value;
  }

  protected void login() {
    login("admin", "easyeasy");
  }

  protected void login(final String user, final String password) {
    login.signIn(user, password);
    login.assertIsConnectedAs(user);
  }

  protected void logout() {
    login.logout();
  }

}
