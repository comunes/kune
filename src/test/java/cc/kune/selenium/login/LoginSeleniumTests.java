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
