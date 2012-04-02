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
package cc.kune.core.client.auth;

import com.google.gwt.user.client.DOM;

/**
 * Remember user/pass implementation <a href=
 * "http://stackoverflow.com/questions/1245174/is-it-possible-to-implement-cross-browser-username-password-autocomplete-in-gxt"
 * >based in this</a> and <a href=
 * "http://www.sencha.com/forum/showthread.php?72027-Auto-complete-login-form"
 * >this</a>.
 */
public class LoginRememberManagerImpl implements LoginRememberManager {

  /** The login forms are in ws.html **/
  private static final String PASSWORD = "password";
  private static final String USERNAME = "username";
  private static final String VALUE = "value";

  public static native String getElementValue(String domId) /*-{
		return $doc.getElementById(domId).value;
  }-*/;

  /**
   * login.submit() only works in FF // $doc.getElementById("login").submit();
   * http
   * ://stackoverflow.com/questions/4254284/browser-stored-username-passwords
   * -in-chrome
   */
  @Override
  public native void clickFormLogin() /*-{
		$doc.getElementById("loginsubmit").click();
		$doc.getElementById("login").submit();
  }-*/;

  @Override
  public String getNickOrEmail() {
    return getElementValue(USERNAME);
  }

  @Override
  public String getPassword() {
    return getElementValue(PASSWORD);
  }

  @Override
  public void setNickOrEmail(final String username) {
    DOM.getElementById(USERNAME).setAttribute(VALUE, username);
  }

  @Override
  public void setPassword(final String password) {
    DOM.getElementById(PASSWORD).setAttribute(VALUE, password);
  }

}
