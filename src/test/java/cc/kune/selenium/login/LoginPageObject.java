package cc.kune.selenium.login;

import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.core.client.auth.SignInForm;
import cc.kune.core.client.auth.SignInPanel;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sitebar.SitebarSignInLink;
import cc.kune.core.client.sitebar.SitebarSignOutLink;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.tools.I18nHelper;
import cc.kune.selenium.tools.SeleniumConstants;

import com.calclab.hablar.login.client.LoginMessages;

public class LoginPageObject extends PageObject {

  @FindBy(id = GWTDEV + SignInPanel.SIGN_IN_BUTTON_ID)
  private RenderedWebElement button;
  private final I18nHelper i18n;
  @FindBy(id = SignInForm.PASSWORD_FIELD_ID + "-input")
  private RenderedWebElement passwd;
  @FindBy(id = GWTDEV + SitebarSignInLink.SITE_SIGN_IN)
  private RenderedWebElement signInLink;
  @FindBy(id = GWTDEV + SitebarSignOutLink.SITE_SIGN_OUT)
  private RenderedWebElement signOutLink;
  @FindBy(id = SignInForm.USER_FIELD_ID + "-input")
  private RenderedWebElement user;
  @FindBy(id = GWTDEV + SiteUserOptionsPresenter.LOGGED_USER_MENU_ID)
  private RenderedWebElement userMenu;

  public LoginPageObject() {
    i18n = new I18nHelper(LoginMessages.class);
  }

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

  public RenderedWebElement getHeader() {
    return signInLink;
  }

  public RenderedWebElement header() {
    return getHeader();
  }

  public void logout() {
    signOutLink.click();
    assertIsDisconnected();
  }

  public void signIn(final String username, final String password) {
    assertIsDisconnected();
    signInLink.click();
    user.clear();
    passwd.clear();
    user.sendKeys(username);
    passwd.sendKeys(password);
    button.click();
  }

  public void signInDefUser() {
    signIn(SeleniumConstants.USERNAME, SeleniumConstants.PASSWD);
  }

}
