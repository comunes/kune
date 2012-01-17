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
package cc.kune.core.server.rpc;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.util.Base64;
import org.json.JSONObject;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.authentication.SessionManager;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.EmailHashInvalidException;
import cc.kune.core.client.errors.EmailNotFoundException;
import cc.kune.core.client.errors.UserAuthException;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.server.UserSession;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.manager.impl.EmailConfirmationType;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.properties.ReservedWordsRegistry;
import cc.kune.core.server.users.UserInfo;
import cc.kune.core.server.users.UserInfoService;
import cc.kune.core.shared.SessionConstants;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.dto.WaveClientParams;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;
import cc.kune.wave.server.CustomWaveClientServlet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;

public class UserRPC implements RPC, UserService {

  private final ContentRPC contentRPC;
  private final Mapper mapper;
  private final ReservedWordsRegistry reserverdWords;
  private final UserFinder userFinder;
  private final UserInfoService userInfoService;
  private final UserManager userManager;
  private final UserSessionManager userSessionManager;
  private final Boolean useSocketIO;
  private final CustomWaveClientServlet waveClientServlet;
  private final SessionManager waveSessionManager;

  @Inject
  public UserRPC(final Provider<UserSession> userSessionProvider, final UserManager userManager,
      @Named(CoreSettings.USE_SOCKETIO) final Boolean useSocketIO,
      final UserInfoService userInfoService, final Mapper mapper,
      final SessionManager waveSessionManager, final CustomWaveClientServlet waveClientServlet,
      final ReservedWordsRegistry reserverdWords, final ContentRPC contentRPC,
      final UserSessionManager userSessionManager, final UserFinder userFinder) {
    this.userManager = userManager;
    this.useSocketIO = useSocketIO;
    this.userInfoService = userInfoService;
    this.mapper = mapper;
    this.waveSessionManager = waveSessionManager;
    this.waveClientServlet = waveClientServlet;
    this.reserverdWords = reserverdWords;
    this.contentRPC = contentRPC;
    this.userSessionManager = userSessionManager;
    this.userFinder = userFinder;
  }

  @Authenticated
  @Override
  @Transactional
  public void askForEmailConfirmation(final String userHash) throws DefaultException {
    final User user = userSessionManager.getUser();
    userManager.askForEmailConfirmation(user, EmailConfirmationType.emailVerification);
  }

  @Override
  @Transactional
  public void askForPasswordReset(final String email) throws EmailNotFoundException {
    try {
      final User user = userFinder.findByEmail(email);
      userManager.askForEmailConfirmation(user, EmailConfirmationType.passwordReset);
    } catch (final NoResultException e) {
      throw new EmailNotFoundException();
    }
  }

  @Override
  @Authenticated
  @Transactional
  public void changePasswd(final String userHash, final String oldPassword, final String newPassword)
      throws DefaultException {
    final Long userId = userSessionManager.getUser().getId();
    userManager.changePasswd(userId, oldPassword, newPassword, true);
  }

  @Override
  @Transactional(rollbackOn = DefaultException.class)
  public void createUser(final UserDTO userDTO, final boolean wantPersonalHomepage)
      throws DefaultException {
    reserverdWords.check(userDTO.getShortName(), userDTO.getName());
    userManager.createUser(userDTO.getShortName(), userDTO.getName(), userDTO.getEmail(),
        userDTO.getPassword(), userDTO.getLanguage().getCode(), userDTO.getCountry().getCode(),
        userDTO.getTimezone().getId(), wantPersonalHomepage);
  }

  @Override
  @Authenticated
  @Transactional
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  public String getUserAvatarBaser64(final String userHash, final StateToken userToken)
      throws DefaultException {
    final User user = userSessionManager.getUser();
    final Group userGroup = user.getUserGroup();
    if (!userGroup.getShortName().equals(userToken.getGroup())) {
      throw new AccessViolationException();
    }
    if (userGroup.hasLogo()) {
      return Base64.encodeBytes(userGroup.getLogo());
    } else {
      throw new DefaultException("Unexpected programatic exception (user has no logo)");
    }
  }

  @Override
  @Authenticated(mandatory = true)
  public WaveClientParams getWaveClientParameters(final String userHash) {
    final HttpSession sessionFromToken = waveSessionManager.getSessionFromToken(userHash);
    final JSONObject sessionJson = waveClientServlet.getSessionJson(sessionFromToken);
    final JSONObject clientFlags = new JSONObject(); // waveClientServlet.getClientFlags();
    return new WaveClientParams(sessionJson.toString(), clientFlags.toString(), useSocketIO);
  };

  private UserInfoDTO loadUserInfo(final User user) throws DefaultException {
    final UserInfo userInfo = userInfoService.buildInfo(user, userSessionManager.getHash());
    return mapper.map(userInfo, UserInfoDTO.class);
  }

  @Override
  @Transactional
  public UserInfoDTO login(final String nickOrEmail, final String passwd, final String waveToken)
      throws DefaultException {
    // final SessionService sessionService = sessionServiceProvider.get();
    // sessionService.getNewSession();
    final User user = userManager.login(nickOrEmail, passwd);
    return loginUser(user, waveToken);
  }

  private UserInfoDTO loginUser(final User user, final String waveToken) throws DefaultException {
    if (user != null) {
      userSessionManager.login(user.getId(), waveToken);
      return loadUserInfo(user);
    } else {
      throw new UserAuthException();
    }
  }

  @Override
  @Authenticated
  @Transactional
  public void logout(final String userHash) throws DefaultException {
    userSessionManager.logout();
  }

  @Override
  @Authenticated(mandatory = false)
  @Transactional
  public void onlyCheckSession(final String userHash) throws DefaultException {
    // Do almost nothing @Authenticated checks user session
    userSessionManager.updateLoggedUser();
  }

  @Override
  @Authenticated
  @Transactional
  public UserInfoDTO reloadUserInfo(final String userHash) throws DefaultException {
    final User user = userSessionManager.getUser();
    userSessionManager.updateLoggedUser();
    return loadUserInfo(user);
  }

  @Override
  @Transactional
  public void resetPassword(final String passwdHash, final String newpasswd) {
    try {
      final User user = userFinder.findByHash(passwdHash);
      userManager.changePasswd(user.getId(), null, newpasswd, false);
    } catch (final NoResultException e) {
      throw new EmailHashInvalidException();
    }
  }

  @Override
  @Authenticated(mandatory = true)
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
  @Transactional
  public void setBuddiesVisibility(final String userHash, final StateToken groupToken,
      final UserSNetVisibility visibility) {
    final User user = userSessionManager.getUser();
    if (!groupToken.getGroup().equals(user.getShortName())) {
      throw new AccessViolationException();
    }
    userManager.setSNetVisibility(user, visibility);
  }

  @Override
  @Authenticated
  @Transactional
  public StateAbstractDTO updateUser(final String userHash, final UserDTO user,
      final I18nLanguageSimpleDTO lang) throws DefaultException {
    final Long id = userSessionManager.getUser().getId();
    if (!id.equals(user.getId())) {
      throw new AccessViolationException();
    }
    final User userUpdated = userManager.update(id, user, lang);
    userSessionManager.updateLoggedUser();
    return contentRPC.getContent(userHash, userUpdated.getUserGroup().getStateToken());
  }

  @Authenticated
  @Override
  @Transactional
  public void verifyPasswordHash(final String userHash, final String emailReceivedHash) {
    final User user = userSessionManager.getUser();
    userManager.verifyPasswordHash(user.getId(), emailReceivedHash, SessionConstants._AN_HOUR);
  }

}
