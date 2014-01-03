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
package cc.kune.core.client.rpcservices;

import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.UserBuddiesPresenceDataDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserServiceAsync.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface UserServiceAsync {

  /**
   * Ask for email confirmation.
   * 
   * @param userHash
   *          the user hash
   * @param callback
   *          the callback
   */
  void askForEmailConfirmation(String userHash, AsyncCallback<Void> callback);

  /**
   * Ask for password reset.
   * 
   * @param email
   *          the email
   * @param callback
   *          the callback
   */
  void askForPasswordReset(String email, AsyncCallback<Void> callback);

  /**
   * Change passwd.
   * 
   * @param userHash
   *          the user hash
   * @param oldPassword
   *          the old password
   * @param newPassword
   *          the new password
   * @param callback
   *          the callback
   */
  void changePasswd(String userHash, String oldPassword, String newPassword, AsyncCallback<Void> callback);

  /**
   * Check user and hash.
   * 
   * @param username
   *          the username
   * @param userHash
   *          the user hash
   * @param callback
   *          the callback
   */
  void checkUserAndHash(String username, String userHash, AsyncCallback<Void> callback);

  /**
   * Creates the user.
   * 
   * @param user
   *          the user
   * @param wantPersonalHomepage
   *          the want personal homepage
   * @param asyncCallback
   *          the async callback
   */
  void createUser(UserDTO user, boolean wantPersonalHomepage, AsyncCallback<Void> asyncCallback);

  /**
   * Gets the buddies presence.
   * 
   * @param userHash
   *          the user hash
   * @param asyncCallback
   *          the async callback
   * @return the buddies presence
   */
  void getBuddiesPresence(String userHash, AsyncCallback<UserBuddiesPresenceDataDTO> asyncCallback);

  /**
   * Gets the user avatar baser64.
   * 
   * @param userHash
   *          the user hash
   * @param userToken
   *          the user token
   * @param asyncCallback
   *          the async callback
   * @return the user avatar baser64
   */
  void getUserAvatarBaser64(String userHash, StateToken userToken, AsyncCallback<String> asyncCallback);

  /**
   * Login.
   * 
   * @param nickOrEmail
   *          the nick or email
   * @param passwd
   *          the passwd
   * @param waveCookieValue
   *          the wave cookie value
   * @param asyncCallback
   *          the async callback
   */
  void login(String nickOrEmail, String passwd, String waveCookieValue,
      AsyncCallback<UserInfoDTO> asyncCallback);

  /**
   * Logout.
   * 
   * @param userHash
   *          the user hash
   * @param asyncCallback
   *          the async callback
   */
  void logout(String userHash, AsyncCallback<Void> asyncCallback);

  /**
   * Only check session.
   * 
   * @param userHash
   *          the user hash
   * @param asyncCallback
   *          the async callback
   */
  void onlyCheckSession(String userHash, AsyncCallback<Void> asyncCallback);

  void preLoginWithEmail(String email, String passwd, AsyncCallback<String> callback);

  /**
   * Reload user info.
   * 
   * @param userHash
   *          the user hash
   * @param asyncCallback
   *          the async callback
   */
  void reloadUserInfo(String userHash, AsyncCallback<UserInfoDTO> asyncCallback);

  /**
   * Reset password.
   * 
   * @param passwdHash
   *          the passwd hash
   * @param newpasswd
   *          the newpasswd
   * @param callback
   *          the callback
   */
  void resetPassword(String passwdHash, String newpasswd, AsyncCallback<Void> callback);

  /**
   * Sets the buddies visibility.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param visibility
   *          the visibility
   * @param asyncCallback
   *          the async callback
   */
  void setBuddiesVisibility(String userHash, StateToken groupToken, UserSNetVisibility visibility,
      AsyncCallback<Void> asyncCallback);

  /**
   * Update user.
   * 
   * @param userHash
   *          the user hash
   * @param user
   *          the user
   * @param lang
   *          the lang
   * @param callback
   *          the callback
   */
  void updateUser(String userHash, UserDTO user, I18nLanguageSimpleDTO lang,
      AsyncCallback<StateAbstractDTO> callback);

  /**
   * Verify password hash.
   * 
   * @param userHash
   *          the user hash
   * @param emailReceivedHash
   *          the email received hash
   * @param asyncCallback
   *          the async callback
   */
  void verifyPasswordHash(String userHash, String emailReceivedHash, AsyncCallback<Void> asyncCallback);
}
