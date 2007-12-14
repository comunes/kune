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

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.users.UserInfo;
import org.ourproject.kune.platf.server.users.UserInfoService;
import org.ourproject.kune.sitebar.client.rpc.UserService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class UserRPC implements RPC, UserService {

    private final UserManager userManager;
    private final UserSession userSession;
    private final GroupManager groupManager;
    private final Mapper mapper;
    private final UserInfoService userInfoService;

    // private final Provider<HttpServletRequest> sessionProvider;

    @Inject
    public UserRPC(final UserSession session, final UserManager userManager, final GroupManager groupManager,
            final UserInfoService userInfoService, final Mapper mapper) {
        // final Provider<HttpServletRequest> sessionProvider,
        // this.sessionProvider = sessionProvider;
        this.userSession = session;
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

    @Transactional(type = TransactionType.READ_ONLY)
    public void logout() throws SerializableException {
        // FIXME: the invalidate is not working (UserSession injection problem
        // within sessions)
        // getHttpSession().invalidate();
        // sessionProvider.get().getSession(true);
        userSession.logout();
    }

    @Transactional(type = TransactionType.READ_WRITE, rollbackOn = SerializableException.class)
    public UserInfoDTO createUser(final String shortName, final String longName, final String email,
            final String passwd, final LicenseDTO license, final String language, final String country,
            final String timezone) throws SerializableException {
        User user = userManager.createUser(shortName, longName, email, passwd, language, country, timezone);
        groupManager.createUserGroup(user);
        return loginUser(user);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public UserInfoDTO reloadUserInfo(final String userHash) throws SerializableException {
        if (userSession.getUser().getId() == null) {
            throw new UserMustBeLoggedException();
        }
        User user = userSession.getUser();
        return loadUserInfo(user);
    }

    private UserInfoDTO loginUser(final User user) throws SerializableException {
        if (user != null) {
            // Maybe use terracotta.org for http session clustering
            userSession.login(user, "FIXME_HERE_GENERATE_ANOTHER_USER_SESSION_ID"); // getHttpSession().getId());
            return loadUserInfo(user);
        } else {
            throw new UserAuthException();
        }
    }

    private UserInfoDTO loadUserInfo(final User user) throws SerializableException {
        UserInfo userInfo = userInfoService.buildInfo(user, userSession.getHash());
        return mapper.map(userInfo, UserInfoDTO.class);
    }

    // private HttpSession getHttpSession() {
    // return sessionProvider.get().getSession();
    // }

}
