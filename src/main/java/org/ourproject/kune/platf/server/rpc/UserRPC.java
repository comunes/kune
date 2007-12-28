/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.rpc;

import java.util.UUID;

import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.users.UserInfo;
import org.ourproject.kune.platf.server.users.UserInfoService;
import org.ourproject.kune.sitebar.client.rpc.UserService;

import com.google.gwt.user.client.rpc.SerializableException;
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

    // private final Provider<HttpServletRequest> sessionProvider;

    @Inject
    public UserRPC(final Provider<UserSession> userSessionProvider, final UserManager userManager,
            final GroupManager groupManager, final UserInfoService userInfoService, final Mapper mapper) {
        // final Provider<HttpServletRequest> sessionProvider,
        // this.sessionProvider = sessionProvider;
        this.userSessionProvider = userSessionProvider;
        this.userManager = userManager;
        this.groupManager = groupManager;
        this.userInfoService = userInfoService;
        this.mapper = mapper;
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public UserInfoDTO login(final String nickOrEmail, final String passwd) throws SerializableException {
        User user = userManager.login(nickOrEmail, passwd);
        return loginUser(user);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    public void logout(final String userHash) throws SerializableException {
        // FIXME: the invalidate is not working (UserSession injection problem
        // within sessions)
        // getHttpSession().invalidate();
        // sessionProvider.get().getSession(true);
        getUserSession().logout();
    }

    @Transactional(type = TransactionType.READ_WRITE, rollbackOn = SerializableException.class)
    public UserInfoDTO createUser(final UserDTO userDTO) throws SerializableException {
        User user = userManager.createUser(userDTO.getShortName(), userDTO.getName(), userDTO.getEmail(), userDTO
                .getPassword(), userDTO.getLanguage().getCode(), userDTO.getCountry().getCode(), userDTO.getTimezone()
                .getId());
        groupManager.createUserGroup(user);
        return loginUser(user);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    public UserInfoDTO reloadUserInfo(final String userHash) throws SerializableException {
        UserSession userSession = getUserSession();
        if (userSession.getUser().getId() == null) {
            throw new UserMustBeLoggedException();
        }
        User user = userSession.getUser();
        return loadUserInfo(user);
    }

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

    private UserInfoDTO loginUser(final User user) throws SerializableException {
        if (user != null) {
            // Maybe use terracotta.org for http session clustering
            getUserSession().login(user, UUID.randomUUID().toString());
            return loadUserInfo(user);
        } else {
            throw new UserAuthException();
        }
    }

    private UserInfoDTO loadUserInfo(final User user) throws SerializableException {
        UserInfo userInfo = userInfoService.buildInfo(user, getUserSession().getHash());
        return mapper.map(userInfo, UserInfoDTO.class);
    }

    // private HttpSession getHttpSession() {
    // return sessionProvider.get().getSession();
    // }

}
