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

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cc.kune.core.client.auth.AnonUsersManager;
import cc.kune.core.client.auth.SignInForm;
import cc.kune.core.client.auth.SignInPanel;
import cc.kune.selenium.SeleniumConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginPageObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LoginPageObject extends AbstractLoginObject {

  /** The anon welcome. */
  @FindBy(id = SeleniumConstants.GWTDEV + AnonUsersManager.ANON_MESSAGE_CLOSE_ICON)
  private WebElement anonWelcome;

  /** The create one link. */
  @FindBy(id = SeleniumConstants.GWTDEV + SignInPanel.CREATE_ONE)
  protected WebElement createOneLink;

  /** The passwd. */
  @FindBy(id = SignInForm.PASSWORD_FIELD_ID)
  private WebElement passwd;

  /** The sign in button. */
  @FindBy(id = SeleniumConstants.GWTDEV + SignInPanel.SIGN_IN_BUTTON_ID)
  private WebElement signInButton;

  /** The user. */
  @FindBy(id = SignInForm.USER_FIELD_ID)
  private WebElement user;

  /**
   * Instantiates a new login page object.
   */
  public LoginPageObject() {
  }

  /**
   * Creates the one.
   */
  public void createOne() {
    assertIsDisconnected();
    // hightlight(signInLink);
    signInLink.click();
    // hightlight(createOneLink);
    createOneLink.click();
  }

  /**
   * Fill sig in in form.
   * 
   * @param username
   *          the username
   * @param password
   *          the password
   * @param withReturn
   *          the with return
   */
  public void fillSigInInForm(final String username, final String password, final boolean withReturn) {
    clearField(user);
    hightlight(user);
    user.sendKeys(username);
    clearField(passwd);
    hightlight(passwd);
    passwd.sendKeys(password);
    // hightlight(signInButton);
    if (withReturn) {
      passwd.sendKeys(Keys.RETURN);
    } else {
      signInButton.click();
    }
  }

  /**
   * Gets the anon msg.
   * 
   * @return the anon msg
   */
  public WebElement getAnonMsg() {
    return anonWelcome;
  }

  /**
   * Gets the header.
   * 
   * @return the header
   */
  public WebElement getHeader() {
    return signInLink;
  }

  /**
   * Header.
   * 
   * @return the web element
   */
  public WebElement header() {
    return getHeader();
  }

  /**
   * High.
   */
  public void high() {
    hightlight(signInLink);
  }

  /**
   * Sign in.
   * 
   * @param username
   *          the username
   * @param password
   *          the password
   */
  public void signIn(final String username, final String password) {
    signIn(username, password, false);
  }

  /**
   * Sign in.
   * 
   * @param username
   *          the username
   * @param password
   *          the password
   * @param withReturn
   *          the with return
   */
  public void signIn(final String username, final String password, final boolean withReturn) {
    assertIsDisconnected();
    showTooltip(signInLink);
    signInLink.click();
    fillSigInInForm(username, password, withReturn);
  }

  /**
   * Sign in def user.
   */
  public void signInDefUser() {
    signIn(SeleniumConstants.USER_SHORNAME, SeleniumConstants.USER_PASSWD, true);
  }

}
