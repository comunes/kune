
package org.ourproject.kune.platf.server.rpc;

import java.util.UUID;

import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.UserAuthException;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.SessionService;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.users.UserInfo;
import org.ourproject.kune.platf.server.users.UserInfoService;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;

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

    @Transactional(type = TransactionType.READ_ONLY)
    public UserInfoDTO login(final String nickOrEmail, final String passwd) throws SerializableException {
        SessionService sessionService = sessionServiceProvider.get();
        sessionService.getNewSession();
        User user = userManager.login(nickOrEmail, passwd);
        return loginUser(user);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_ONLY)
    public void logout(final String userHash) throws SerializableException {
        getUserSession().logout();
        SessionService sessionService = sessionServiceProvider.get();
        sessionService.getNewSession();
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
        User user = userSession.getUser();
        return loadUserInfo(user);
    }

    @Authenticated(mandatory = false)
    @Transactional(type = TransactionType.READ_ONLY)
    public void onlyCheckSession(final String userHash) throws SerializableException {
        // Do nothing @Authenticated checks user session
    };

    private UserInfoDTO loginUser(final User user) throws SerializableException {
        if (user != null) {
            // Maybe use terracotta.org for http session clustering
            getUserSession().login(user, UUID.randomUUID().toString());
            return loadUserInfo(user);
        } else {
            throw new UserAuthException();
        }
    }

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

    private UserInfoDTO loadUserInfo(final User user) throws SerializableException {
        UserInfo userInfo = userInfoService.buildInfo(user, getUserSession().getHash());
        return mapper.map(userInfo, UserInfoDTO.class);
    }

}
