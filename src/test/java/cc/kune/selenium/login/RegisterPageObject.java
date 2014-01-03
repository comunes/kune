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

import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.RegisterForm;
import cc.kune.core.client.auth.RegisterPanel;
import cc.kune.selenium.SeleniumConstants;
import cc.kune.selenium.SeleniumUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterPageObject.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class RegisterPageObject extends AbstractLoginObject {

  /** The email. */
  @FindBy(id = RegisterForm.EMAIL_FIELD + SeleniumConstants.INPUT)
  private WebElement email;

  /** The long name. */
  @FindBy(id = RegisterForm.LONGNAME_FIELD + SeleniumConstants.INPUT)
  private WebElement longName;

  /** The passwd. */
  @FindBy(id = RegisterForm.PASSWORD_FIELD + SeleniumConstants.INPUT)
  private WebElement passwd;

  /** The register button. */
  @FindBy(id = SeleniumConstants.GWTDEV + RegisterPanel.REGISTER_BUTTON_ID)
  private WebElement registerButton;

  /** The short name. */
  @FindBy(id = RegisterForm.NICK_FIELD + SeleniumConstants.INPUT)
  private WebElement shortName;

  /** The welcome. */
  @FindBy(id = SeleniumConstants.GWTDEV + Register.WELCOME_ID)
  private WebElement welcome;

  /**
   * Instantiates a new register page object.
   */
  public RegisterPageObject() {
    // i18n = new I18nHelper(LoginMessages.class);
  }

  /**
   * Fill register form.
   * 
   * @param nick
   *          the nick
   * @param name
   *          the name
   * @param pass
   *          the pass
   * @param em
   *          the em
   * @param withReturn
   *          the with return
   * @param doScreenshot
   *          the do screenshot
   */
  public void fillRegisterForm(final String nick, final String name, final String pass, final String em,
      final boolean withReturn, final boolean doScreenshot) {
    clearField(shortName);
    hightlight(shortName);
    shortName.sendKeys(nick);
    clearField(longName);
    hightlight(longName);
    longName.sendKeys(name);
    clearField(passwd);
    hightlight(passwd);
    passwd.sendKeys(pass);
    clearField(email);
    hightlight(email);
    email.sendKeys(em);
    hightlight(registerButton);
    if (doScreenshot) {
      SeleniumUtils.doScreenshot(getWebDriver(), "register");
      sleep(2000);
    }
    if (withReturn) {
      email.sendKeys(Keys.RETURN);
    } else {
      registerButton.click();
    }
  }

  /**
   * Gets the welcome msg.
   * 
   * @return the welcome msg
   */
  public WebElement getWelcomeMsg() {
    return welcome;
  }

}
