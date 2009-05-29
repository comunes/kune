/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import java.util.UUID;

import org.jivesoftware.smack.util.Base64;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserBuddiesVisibilityDTO;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.client.rpc.UserService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessRol;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.auth.SessionService;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.domain.UserBuddiesVisibility;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.users.UserInfo;
import org.ourproject.kune.platf.server.users.UserInfoService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class UserRPC implements RPC, UserService {

    private final UserManager userManager;
    private final Provider<UserSession> userSessionProvider;
    private final GroupManager groupManager;
    private final Mapper mapper;
    private final UserInfoService userInfoService;
    private final Provider<SessionService> sessionServiceProvider;

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

    @Transactional(type = TransactionType.READ_WRITE, rollbackOn = DefaultException.class)
    public UserInfoDTO createUser(final UserDTO userDTO, final boolean wantPersonalHomepage) throws DefaultException {
        final User user = userManager.createUser(userDTO.getShortName(), userDTO.getName(), userDTO.getEmail(),
                userDTO.getPassword(), userDTO.getLanguage().getCode(), userDTO.getCountry().getCode(),
                userDTO.getTimezone().getId());
        groupManager.createUserGroup(user, wantPersonalHomepage);
        return login(userDTO.getShortName(), userDTO.getPassword());
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
    public String getUserAvatarBaser64(final String userHash, final StateToken userToken) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        Group userGroup = user.getUserGroup();
        if (!userGroup.getShortName().equals(userToken.getGroup())) {
            throw new AccessViolationException();
        }
        if (userGroup.hasLogo()) {
            return Base64.encodeBytes(userGroup.getLogo());
        } else {
            throw new DefaultException("Unexpected programatic exception (user has no logo)");
        }
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public UserInfoDTO login(final String nickOrEmail, final String passwd) throws DefaultException {
        final SessionService sessionService = sessionServiceProvider.get();
        sessionService.getNewSession();
        final User user = userManager.login(nickOrEmail, passwd);
        return loginUser(user);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    public void logout(final String userHash) throws DefaultException {
        getUserSession().logout();
        final SessionService sessionService = sessionServiceProvider.get();
        sessionService.getNewSession();
    }

    @Authenticated(mandatory = false)
    @Transactional(type = TransactionType.READ_ONLY)
    public void onlyCheckSession(final String userHash) throws DefaultException {
        // Do nothing @Authenticated checks user session
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    public UserInfoDTO reloadUserInfo(final String userHash) throws DefaultException {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        return loadUserInfo(user);
    }

    @Authenticated(mandatory = true)
    @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
    @Transactional(type = TransactionType.READ_WRITE)
    public void setBuddiesVisibility(final String userHash, final StateToken groupToken,
            final UserBuddiesVisibilityDTO visibility) {
        final UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        if (!groupToken.getGroup().equals(user.getShortName())) {
            new AccessViolationException();
        }
        user.setBuddiesVisibility(UserBuddiesVisibility.valueOf(visibility.toString()));
    };

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

    private UserInfoDTO loadUserInfo(final User user) throws DefaultException {
        final UserInfo userInfo = userInfoService.buildInfo(user, getUserSession().getHash());
        final UserInfoDTO map = mapper.map(userInfo, UserInfoDTO.class);
        return map;
    }

    private UserInfoDTO loginUser(final User user) throws DefaultException {
        if (user != null) {
            // Maybe use terracotta.org for http session clustering
            getUserSession().login(user, UUID.randomUUID().toString());
            return loadUserInfo(user);
        } else {
            throw new UserAuthException();
        }
    }

}
