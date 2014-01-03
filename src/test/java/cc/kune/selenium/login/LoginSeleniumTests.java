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

import org.testng.annotations.Test;

import cc.kune.selenium.KuneSeleniumTest;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginSeleniumTests.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LoginSeleniumTests extends KuneSeleniumTest {

  /**
   * Basic sign in.
   * 
   * @param user
   *          the user
   * @param passwd
   *          the passwd
   */
  @Test(dataProvider = "correctlogin")
  public void basicSignIn(final String user, final String passwd) {
    login.assertIsDisconnected();
    showTitleSlide("User sign in", "to get full access to this site");
    login.signIn(user, passwd);
    login.assertIsConnectedAs(user);
    // userSpace.getWavePanel().click();
    login.logout();
    login.assertIsDisconnected();
  }

  /**
   * High.
   */
  @Test
  public void high() {
    login.high();
  }

  /**
   * Severals sign in sing out.
   * 
   * @param user
   *          the user
   * @param passwd
   *          the passwd
   */
  @Test(dataProvider = "correctlogin")
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

  /**
   * Sign in incorrect passwd.
   */
  @Test
  public void signInIncorrectPasswd() {
    login.assertIsDisconnected();
    login.signIn("nouser", "nopassword");
    login.assertIsDisconnected();
  }

  /**
   * Sign in with token.
   * 
   * @param user
   *          the user
   * @param passwd
   *          the passwd
   */
  @Test(dataProvider = "correctlogin")
  public void signInWithToken(final String user, final String passwd) {
    login.assertIsDisconnected();
    login.fillSigInInForm(user, passwd, true);
    login.assertIsConnectedAs(user);
    login.logout();
    login.assertIsDisconnected();
  }
}
