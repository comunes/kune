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

import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.util.Base64;
import org.json.JSONObject;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.authentication.SessionManager;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.UserAuthException;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.server.UserSession;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.auth.SessionService;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.users.UserInfo;
import cc.kune.core.server.users.UserInfoService;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.dto.WaveClientParams;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.wave.server.CustomWaveClientServlet;
import cc.kune.wave.server.KuneWaveManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;

@Singleton
public class UserRPC implements RPC, UserService {

    private final ContentManager contentManager;
    private final GroupManager groupManager;
    private final I18nTranslationService i18n;
    private final KuneWaveManager kuneWaveManager;
    private final Mapper mapper;
    private final Provider<SessionService> sessionServiceProvider;
    private final UserInfoService userInfoService;
    private final UserManager userManager;
    private final Provider<UserSession> userSessionProvider;
    private final Boolean useSocketIO;
    private final CustomWaveClientServlet waveClientServlet;
    private final SessionManager waveSessionManager;

    @Inject
    public UserRPC(final Provider<SessionService> sessionServiceProvider,
            final Provider<UserSession> userSessionProvider, final UserManager userManager,
            @Named(CoreSettings.USE_SOCKETIO) final Boolean useSocketIO, final GroupManager groupManager,
            final UserInfoService userInfoService, final Mapper mapper, final SessionManager waveSessionManager,
            final CustomWaveClientServlet waveClientServlet, final I18nTranslationService i18n,
            final KuneWaveManager kuneWaveManager, final ContentManager contentManager) {

        this.sessionServiceProvider = sessionServiceProvider;
        this.userSessionProvider = userSessionProvider;
        this.userManager = userManager;
        this.useSocketIO = useSocketIO;
        this.groupManager = groupManager;
        this.userInfoService = userInfoService;
        this.mapper = mapper;
        this.waveSessionManager = waveSessionManager;
        this.waveClientServlet = waveClientServlet;
        this.i18n = i18n;
        this.kuneWaveManager = kuneWaveManager;
        this.contentManager = contentManager;
    }

    @Override
    @Transactional(rollbackOn = DefaultException.class)
    public void createUser(final UserDTO userDTO, final boolean wantPersonalHomepage) throws DefaultException {
        final User user = userManager.createUser(userDTO.getShortName(), userDTO.getName(), userDTO.getEmail(),
                userDTO.getPassword(), userDTO.getLanguage().getCode(), userDTO.getCountry().getCode(),
                userDTO.getTimezone().getId());
        final Group userGroup = groupManager.createUserGroup(user, wantPersonalHomepage);
        final String waveId = kuneWaveManager.createWave("<h1>" + i18n.t("[%s] Bio", userDTO.getName()) + "</h1>"
                + i18n.t("This user has not written its biography yet"), user.getShortName());
        contentManager.save(user, userGroup.getDefaultContent(), waveId);
    }

    @Override
    @Authenticated
    @Transactional
    @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
    public String getUserAvatarBaser64(final String userHash, final StateToken userToken) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
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

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

    @Override
    @Authenticated(mandatory = true)
    public WaveClientParams getWaveClientParameters(final String userHash) {
        final HttpSession sessionFromToken = waveSessionManager.getSessionFromToken(userHash);
        final JSONObject sessionJson = waveClientServlet.getSessionJson(sessionFromToken);
        final JSONObject clientFlags = new JSONObject(); // waveClientServlet.getClientFlags();
        return new WaveClientParams(sessionJson.toString(), clientFlags.toString(), useSocketIO);
    }

    private UserInfoDTO loadUserInfo(final User user) throws DefaultException {
        final UserInfo userInfo = userInfoService.buildInfo(user, getUserSession().getHash());
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
            // Maybe use terracotta.org for http session clustering
            getUserSession().login(user, waveToken);
            return loadUserInfo(user);
        } else {
            throw new UserAuthException();
        }
    };

    @Override
    @Authenticated
    @Transactional
    public void logout(final String userHash) throws DefaultException {
        getUserSession().logout();
        // FIXME final SessionService sessionService =
        // sessionServiceProvider.get();
        // FIXME sessionService.getNewSession();
    }

    @Override
    @Authenticated(mandatory = false)
    @Transactional
    public void onlyCheckSession(final String userHash) throws DefaultException {
        // Do nothing @Authenticated checks user session
    }

    @Override
    @Authenticated
    @Transactional
    public UserInfoDTO reloadUserInfo(final String userHash) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        return loadUserInfo(user);
    }

    @Override
    @Authenticated(mandatory = true)
    @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
    @Transactional
    public void setBuddiesVisibility(final String userHash, final StateToken groupToken,
            final UserSNetVisibility visibility) {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        if (!groupToken.getGroup().equals(user.getShortName())) {
            throw new AccessViolationException();
        }
        user.setSNetVisibility(visibility);
    }
}
