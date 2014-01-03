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

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.EmailAddressInUseException;
import cc.kune.core.client.errors.EmailHashExpiredException;
import cc.kune.core.client.errors.EmailHashInvalidException;
import cc.kune.core.client.errors.EmailNotFoundException;
import cc.kune.core.client.errors.GroupLongNameInUseException;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.UserBuddiesPresenceDataDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserService.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RemoteServiceRelativePath("UserService")
public interface UserService extends RemoteService {

  /**
   * Ask for email confirmation.
   * 
   * @param userHash
   *          the user hash
   * @throws EmailHashInvalidException
   *           the email hash invalid exception
   * @throws EmailHashExpiredException
   *           the email hash expired exception
   */
  void askForEmailConfirmation(String userHash) throws EmailHashInvalidException,
      EmailHashExpiredException;

  /**
   * Ask for password reset.
   * 
   * @param email
   *          the email
   * @throws EmailNotFoundException
   *           the email not found exception
   */
  void askForPasswordReset(String email) throws EmailNotFoundException;

  /**
   * Change passwd.
   * 
   * @param userHash
   *          the user hash
   * @param oldPassword
   *          the old password
   * @param newPassword
   *          the new password
   * @throws DefaultException
   *           the default exception
   */
  void changePasswd(String userHash, String oldPassword, String newPassword) throws DefaultException;

  /**
   * Check user and hash (this is only used for external auth, like the openfire
   * chat).
   * 
   * @param username
   *          the username
   * @param userHash
   *          the user hash
   */
  void checkUserAndHash(String username, String userHash);

  /**
   * Creates the user.
   * 
   * @param user
   *          the user
   * @param wantPersonalHomepage
   *          the want personal homepage
   * @throws DefaultException
   *           the default exception
   */
  void createUser(UserDTO user, boolean wantPersonalHomepage) throws DefaultException;

  /**
   * Gets the buddies presence.
   * 
   * @param userHash
   *          the user hash
   * @return the buddies presence
   */
  UserBuddiesPresenceDataDTO getBuddiesPresence(String userHash);

  /**
   * Gets the user avatar baser64.
   * 
   * @param userHash
   *          the user hash
   * @param userToken
   *          the user token
   * @return the user avatar baser64
   * @throws DefaultException
   *           the default exception
   */
  String getUserAvatarBaser64(String userHash, StateToken userToken) throws DefaultException;

  /**
   * Login.
   * 
   * @param nickOrEmail
   *          the nick or email
   * @param passwd
   *          the passwd
   * @param waveToken
   *          the wave token
   * @return the user info dto
   * @throws DefaultException
   *           the default exception
   */
  UserInfoDTO login(String nickOrEmail, String passwd, String waveToken) throws DefaultException;

  /**
   * Logout.
   * 
   * @param userHash
   *          the user hash
   * @throws DefaultException
   *           the default exception
   */
  void logout(String userHash) throws DefaultException;

  /**
   * Only check session.
   * 
   * @param userHash
   *          the user hash
   * @throws DefaultException
   *           the default exception
   */
  void onlyCheckSession(String userHash) throws DefaultException;

  String preLoginWithEmail(String email, String passwd);

  /**
   * Reload user info.
   * 
   * @param userHash
   *          the user hash
   * @return the user info dto
   * @throws DefaultException
   *           the default exception
   */
  UserInfoDTO reloadUserInfo(String userHash) throws DefaultException;

  /**
   * Reset password.
   * 
   * @param passwdHash
   *          the passwd hash
   * @param newpasswd
   *          the newpasswd
   * @throws EmailHashInvalidException
   *           the email hash invalid exception
   */
  void resetPassword(String passwdHash, String newpasswd) throws EmailHashInvalidException;

  /**
   * Sets the buddies visibility.
   * 
   * @param userHash
   *          the user hash
   * @param groupToken
   *          the group token
   * @param visibility
   *          the visibility
   */
  void setBuddiesVisibility(String userHash, StateToken groupToken, UserSNetVisibility visibility);

  /**
   * Update user.
   * 
   * @param userHash
   *          the user hash
   * @param user
   *          the user
   * @param lang
   *          the lang
   * @return the state abstract dto
   * @throws DefaultException
   *           the default exception
   * @throws EmailAddressInUseException
   *           the email address in use exception
   * @throws GroupLongNameInUseException
   *           the group long name in use exception
   */
  StateAbstractDTO updateUser(String userHash, UserDTO user, I18nLanguageSimpleDTO lang)
      throws DefaultException, EmailAddressInUseException, GroupLongNameInUseException;

  /**
   * Verify password hash.
   * 
   * @param userHash
   *          the user hash
   * @param emailReceivedHash
   *          the email received hash
   * @throws EmailHashInvalidException
   *           the email hash invalid exception
   * @throws EmailHashExpiredException
   *           the email hash expired exception
   */
  void verifyPasswordHash(String userHash, String emailReceivedHash) throws EmailHashInvalidException,
      EmailHashExpiredException;

}
