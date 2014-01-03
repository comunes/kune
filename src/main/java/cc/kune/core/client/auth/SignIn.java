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

import cc.kune.common.client.notify.NotifyLevel;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The Interface SignIn.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface SignIn {

  /**
   * Do sign in.
   * 
   * @param nickOrEmail
   *          the nick or email
   * @param passwd
   *          the passwd
   * @param gotoHomePage
   *          the goto home page
   * @param callback
   *          the callback
   */
  void doSignIn(String nickOrEmail, String passwd, boolean gotoHomePage, AsyncCallback<Void> callback);

  /**
   * Hide.
   */
  void hide();

  /**
   * Sets the ask for language change (if the user has a different language that
   * the current kune code, will ask or not to reload the client).
   * 
   * @param ask
   *          the new ask for language change
   */
  void setAskForLanguageChange(boolean ask);

  /**
   * Sets the error message.
   * 
   * @param message
   *          the message
   * @param level
   *          the level
   */
  void setErrorMessage(String message, NotifyLevel level);

  /**
   * Sets the goto token on cancel.
   * 
   * @param gotoToken
   *          the new goto token on cancel
   */
  void setGotoTokenOnCancel(String gotoToken);

  void setHeaderLogo(String url);

  void setTitleIcon(String url);

  /**
   * Show sign in dialog.
   * 
   * @param token
   *          after sing in
   */
  void showSignInDialog(String token);

}
