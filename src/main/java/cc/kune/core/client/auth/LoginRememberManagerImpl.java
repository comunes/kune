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
package cc.kune.core.client.auth;

import com.google.gwt.user.client.DOM;

// TODO: Auto-generated Javadoc
/**
 * Remember user/pass implementation <a href=
 * "http://stackoverflow.com/questions/1245174/is-it-possible-to-implement-cross-browser-username-password-autocomplete-in-gxt"
 * >based in this</a> and <a href=
 * "http://www.sencha.com/forum/showthread.php?72027-Auto-complete-login-form"
 * >this</a>.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LoginRememberManagerImpl implements LoginRememberManager {

  /** The login forms are in ws.html **/
  private static final String PASSWORD = "password";

  /** The Constant USERNAME. */
  private static final String USERNAME = "username";

  /** The Constant VALUE. */
  private static final String VALUE = "value";

  /**
   * Gets the element value.
   * 
   * @param domId
   *          the dom id
   * @return the element value
   */
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
                                      $doc.getElementById("login").submit();
                                      }-*/;

  // $doc.getElementById("loginsubmit").click();

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.LoginRememberManager#getNickOrEmail()
   */
  @Override
  public String getNickOrEmail() {
    return getElementValue(USERNAME);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.auth.LoginRememberManager#getPassword()
   */
  @Override
  public String getPassword() {
    return getElementValue(PASSWORD);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.LoginRememberManager#setNickOrEmail(java.lang.
   * String)
   */
  @Override
  public void setNickOrEmail(final String username) {
    DOM.getElementById(USERNAME).setAttribute(VALUE, username);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.auth.LoginRememberManager#setPassword(java.lang.String)
   */
  @Override
  public void setPassword(final String password) {
    DOM.getElementById(PASSWORD).setAttribute(VALUE, password);
  }

}
