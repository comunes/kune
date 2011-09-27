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

import cc.kune.core.client.auth.RegisterForm;
import cc.kune.core.client.auth.RegisterPanel;
import cc.kune.selenium.tools.SeleniumConstants;

public class RegisterPageObject extends AbstractLoginObject {

  @FindBy(id = RegisterForm.EMAIL_FIELD + SeleniumConstants.INPUT)
  private WebElement email;
  @FindBy(id = RegisterForm.LONGNAME_FIELD + SeleniumConstants.INPUT)
  private WebElement longName;
  @FindBy(id = RegisterForm.PASSWORD_FIELD + SeleniumConstants.INPUT)
  private WebElement passwd;
  @FindBy(id = SeleniumConstants.GWTDEV + RegisterPanel.REGISTER_BUTTON_ID)
  private WebElement registerButton;
  @FindBy(id = RegisterForm.NICK_FIELD + SeleniumConstants.INPUT)
  private WebElement shortName;

  public RegisterPageObject() {
    // i18n = new I18nHelper(LoginMessages.class);
  }

  public void fillRegisterForm(final String nick, final String name, final String pass, final String em,
      final boolean withReturn) {
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
    if (withReturn) {
      email.sendKeys(Keys.RETURN);
    } else {
      registerButton.click();
    }
  }

}
