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
package org.ourproject.kune.platf.server.rpc;

import org.jivesoftware.smack.util.Base64;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.auth.SessionService;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.users.UserInfo;
import org.ourproject.kune.platf.server.users.UserInfoService;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.UserAuthException;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.UserBuddiesVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.UserDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class UserRPC implements RPC, UserService {

    private final GroupManager groupManager;
    private final Mapper mapper;
    private final Provider<SessionService> sessionServiceProvider;
    private final UserInfoService userInfoService;
    private final UserManager userManager;
    private final Provider<UserSession> userSessionProvider;

    @Inject
    public UserRPC(final Provider<SessionService> sessionServiceProvider,
            final Provider<UserSession> userSessionProvider, final UserManager userManager,
            final GroupManager groupManager, final UserInfoService userInfoService, final Mapper mapper) {

        this.sessionServiceProvider = sessionServiceProvider;
        this.userSessionProvider = userSessionProvider;
        this.userManager = userManager;
        this.groupManager = groupManager;
        this.userInfoService = userInfoService;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackOn = DefaultException.class)
    public void createUser(final UserDTO userDTO, final boolean wantPersonalHomepage) throws DefaultException {
        final User user = userManager.createUser(userDTO.getShortName(), userDTO.getName(), userDTO.getEmail(),
                userDTO.getPassword(), userDTO.getLanguage().getCode(), userDTO.getCountry().getCode(),
                userDTO.getTimezone().getId());
        groupManager.createUserGroup(user, wantPersonalHomepage);
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
    }

    @Override
    @Authenticated
    @Transactional
    public void logout(final String userHash) throws DefaultException {
        getUserSession().logout();
        // FIXME final SessionService sessionService =
        // sessionServiceProvider.get();
        // FIXME sessionService.getNewSession();
    };

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
            final UserBuddiesVisibility visibility) {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        if (!groupToken.getGroup().equals(user.getShortName())) {
            throw new AccessViolationException();
        }
        user.setBuddiesVisibility(visibility);
    }

}
