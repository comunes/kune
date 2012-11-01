/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
import cc.kune.core.shared.dto.WaveClientParams;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserService")
public interface UserService extends RemoteService {

  void askForEmailConfirmation(String userHash) throws EmailHashInvalidException,
      EmailHashExpiredException;

  void askForPasswordReset(String email) throws EmailNotFoundException;

  void changePasswd(String userHash, String oldPassword, String newPassword) throws DefaultException;

  /**
   * Check user and hash (this is only used for external auth, like the openfire
   * chat)
   * 
   * @param username
   *          the username
   * @param userHash
   *          the user hash
   */
  void checkUserAndHash(String username, String userHash);

  void createUser(UserDTO user, boolean wantPersonalHomepage) throws DefaultException;

  UserBuddiesPresenceDataDTO getBuddiesPresence(String userHash);

  String getUserAvatarBaser64(String userHash, StateToken userToken) throws DefaultException;

  WaveClientParams getWaveClientParameters(String userHash);

  UserInfoDTO login(String nickOrEmail, String passwd, String waveToken) throws DefaultException;

  void logout(String userHash) throws DefaultException;

  void onlyCheckSession(String userHash) throws DefaultException;

  UserInfoDTO reloadUserInfo(String userHash) throws DefaultException;

  void resetPassword(String passwdHash, String newpasswd) throws EmailHashInvalidException;

  void setBuddiesVisibility(String userHash, StateToken groupToken, UserSNetVisibility visibility);

  StateAbstractDTO updateUser(String userHash, UserDTO user, I18nLanguageSimpleDTO lang)
      throws DefaultException, EmailAddressInUseException, GroupLongNameInUseException;

  void verifyPasswordHash(String userHash, String emailReceivedHash) throws EmailHashInvalidException,
      EmailHashExpiredException;

}
