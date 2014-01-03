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
package cc.kune.core.server.manager;

import org.waveprotocol.box.server.authentication.PasswordDigest;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.EmailHashExpiredException;
import cc.kune.core.client.errors.EmailHashInvalidException;
import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.core.server.manager.impl.EmailConfirmationType;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.UserBuddiesPresenceDataDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.domain.User;
import cc.kune.domain.UserBuddiesData;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserManager.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface UserManager {

  /**
   * Ask for email confirmation.
   * 
   * @param user
   *          the user
   * @param type
   *          the type
   * @throws DefaultException
   *           the default exception
   */
  void askForEmailConfirmation(User user, EmailConfirmationType type) throws DefaultException;

  /**
   * Change passwd.
   * 
   * @param userId
   *          the user id to change the passwd
   * @param oldPassword
   *          the old password
   * @param newPassword
   *          the new password
   * @param checkOldPasswd
   *          if we have to check the old passwd (not necessary if we are
   *          reseting a unknown password)
   * @return the user
   */
  User changePasswd(Long userId, String oldPassword, String newPassword, boolean checkOldPasswd);

  /**
   * Clear password hash (after been used).
   * 
   * @param user
   *          the user
   */
  void clearPasswordHash(User user);

  /**
   * CreateUser new method with language country and timezone params.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   * @param email
   *          the email
   * @param passwd
   *          the passwd
   * @param language
   *          the language
   * @param country
   *          the country
   * @param timezone
   *          the timezone
   * @param wantPersonalHomepage
   *          the want personal homepage
   * @return User
   * @throws I18nNotFoundException
   *           the i18n not found exception
   */
  User createUser(String shortName, String longName, String email, String passwd, String language,
      String country, String timezone, boolean wantPersonalHomepage) throws I18nNotFoundException;

  /**
   * Creates the wave account.
   * 
   * @param shortName
   *          the short name
   * @param passwdDigest
   *          the passwd digest
   */
  void createWaveAccount(String shortName, PasswordDigest passwdDigest);

  /**
   * IMPORTANT: if userId == null, it returns User.UNKNOWN_USER
   * 
   * @param userId
   *          the user id
   * @return the user
   */
  User find(Long userId);

  /**
   * Find by shortname.
   * 
   * @param shortName
   *          the short name
   * @return the user
   */
  User findByShortname(String shortName);

  /**
   * Gets the buddies presence (mainly the last connected time).
   * 
   * @param user
   *          the user
   * @return the buddies presence
   */
  UserBuddiesPresenceDataDTO getBuddiesPresence(User user);

  /**
   * Gets the user buddies.
   * 
   * @param shortName
   *          the short name
   * @return the user buddies
   */
  UserBuddiesData getUserBuddies(String shortName);

  /**
   * Login.
   * 
   * @param nickOrEmail
   *          the nick or email
   * @param passwd
   *          the passwd
   * @return the user
   */
  User login(String nickOrEmail, String passwd);

  /**
   * Re index.
   */
  void reIndex();

  /**
   * Search.
   * 
   * @param search
   *          the search
   * @return the search result
   */
  SearchResult<User> search(String search);

  /**
   * Search.
   * 
   * @param search
   *          the search
   * @param firstResult
   *          the first result
   * @param maxResults
   *          the max results
   * @return the search result
   */
  SearchResult<User> search(String search, Integer firstResult, Integer maxResults);

  /**
   * Sets the s net visibility.
   * 
   * @param user
   *          the user
   * @param visibility
   *          the visibility
   */
  void setSNetVisibility(User user, UserSNetVisibility visibility);

  /**
   * Update.
   * 
   * @param userId
   *          the userId to change
   * @param user
   *          the userDTO with values to change
   * @param lang
   *          the lang
   * @return the User after updated
   */
  User update(Long userId, UserDTO user, I18nLanguageSimpleDTO lang);

  /**
   * Verify password hash of a user.
   * 
   * @param userId
   *          the user id
   * @param emailReceivedHash
   *          the email received hash
   * @param period
   *          the period (1h or more if is a new account);
   * @throws EmailHashInvalidException
   *           the email hash invalid exception
   * @throws EmailHashExpiredException
   *           the email hash expired exception
   */
  void verifyPasswordHash(Long userId, String emailReceivedHash, long period)
      throws EmailHashInvalidException, EmailHashExpiredException;

}
