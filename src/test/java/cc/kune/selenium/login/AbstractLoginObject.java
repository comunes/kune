/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sitebar.SitebarSignInLink;
import cc.kune.core.client.sitebar.SitebarSignOutLink;
import cc.kune.selenium.PageObject;
import cc.kune.selenium.SeleniumConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractLoginObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AbstractLoginObject extends PageObject {

  /** The sign in link. */
  @FindBy(id = SeleniumConstants.GWTDEV + SitebarSignInLink.SITE_SIGN_IN)
  protected WebElement signInLink;

  /** The sign out link. */
  @FindBy(id = SeleniumConstants.GWTDEV + SitebarSignOutLink.SITE_SIGN_OUT)
  protected WebElement signOutLink;

  /** The user menu. */
  @FindBy(id = SeleniumConstants.GWTDEV + SiteUserOptionsPresenter.LOGGED_USER_MENU_ID)
  protected WebElement userMenu;

  /**
   * Assert is connected as.
   * 
   * @param user
   *          the user
   */
  public void assertIsConnectedAs(final String user) {
    waitFor(userMenu, user);
  }

  /**
   * Assert is disconnected.
   */
  public void assertIsDisconnected() {
    try {
      signOutLink.click();
    } catch (final Exception e) {
    }
    waitFor(signInLink);
  }

  /**
   * Logout.
   */
  public void logout() {
    hightlight(signOutLink);
    signOutLink.click();
    assertIsDisconnected();
  }

  /**
   * Sign out.
   */
  public void signOut() {
    logout();
  }

}
