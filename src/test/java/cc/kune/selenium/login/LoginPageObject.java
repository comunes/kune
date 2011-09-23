/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.selenium.login;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
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

  private final I18nHelper i18n;
  @FindBy(id = SignInForm.PASSWORD_FIELD_ID + "-input")
  private WebElement passwd;
  @FindBy(id = GWTDEV + SignInPanel.SIGN_IN_BUTTON_ID)
  private WebElement signInButton;
  @FindBy(id = GWTDEV + SitebarSignInLink.SITE_SIGN_IN)
  private WebElement signInLink;
  @FindBy(id = GWTDEV + SitebarSignOutLink.SITE_SIGN_OUT)
  private WebElement signOutLink;
  @FindBy(id = SignInForm.USER_FIELD_ID + "-input")
  private WebElement user;
  @FindBy(id = GWTDEV + SiteUserOptionsPresenter.LOGGED_USER_MENU_ID)
  private WebElement userMenu;

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

  public void fillSigInInForm(final String username, final String password, final boolean withReturn) {
    user.clear();
    passwd.clear();
    user.clear();
    passwd.clear();
    user.sendKeys(username);
    passwd.sendKeys(password);
    if (withReturn) {
      passwd.sendKeys(Keys.RETURN);
    } else {
      signInButton.click();
    }
  }

  public WebElement getHeader() {
    return signInLink;
  }

  public WebElement header() {
    return getHeader();
  }

  public void logout() {
    signOutLink.click();
    assertIsDisconnected();
  }

  public void signIn(final String username, final String password) {
    signIn(username, password, true);
  }

  public void signIn(final String username, final String password, final boolean withReturn) {
    assertIsDisconnected();
    signInLink.click();
    fillSigInInForm(username, password, withReturn);
  }

  public void signInDefUser() {
    signIn(SeleniumConstants.USERNAME, SeleniumConstants.PASSWD, false);
  }

  public void signOut() {
    logout();
  }

}
