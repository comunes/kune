package cc.kune.selenium.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sitebar.SitebarSignInLink;
import cc.kune.core.client.sitebar.SitebarSignOutLink;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.tools.SeleniumConstants;

public class AbstractLoginObject extends PageObject {
  @FindBy(id = SeleniumConstants.GWTDEV + SitebarSignInLink.SITE_SIGN_IN)
  protected WebElement signInLink;
  @FindBy(id = SeleniumConstants.GWTDEV + SitebarSignOutLink.SITE_SIGN_OUT)
  protected WebElement signOutLink;
  @FindBy(id = SeleniumConstants.GWTDEV + SiteUserOptionsPresenter.LOGGED_USER_MENU_ID)
  protected WebElement userMenu;

  public void assertIsConnectedAs(final String user) {
    waitFor(userMenu, user);
  }

  public void assertIsDisconnected() {
    try {
      signOutLink.click();
    } catch (final Exception e) {
    }
    waitFor(signInLink);
  }

  public void logout() {
    hightlight(signOutLink);
    signOutLink.click();
    assertIsDisconnected();
  }

  public void signOut() {
    logout();
  }

}
