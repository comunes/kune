
package org.ourproject.kune.platf.server.rpc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessType;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class GroupRPC implements RPC, GroupService {
    private final Mapper mapper;
    private final GroupManager groupManager;
    private final Provider<UserSession> userSessionProvider;
    private static final Log log = LogFactory.getLog(GroupRPC.class);

    @Inject
    public GroupRPC(final Provider<UserSession> userSessionProvider, final GroupManager groupManager,
            final Mapper mapper) {
        this.userSessionProvider = userSessionProvider;
        this.groupManager = groupManager;
        this.mapper = mapper;
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE, rollbackOn = SerializableException.class)
    public StateToken createNewGroup(final String userHash, final GroupDTO groupDTO) throws SerializableException {
        log.debug(groupDTO.getShortName() + groupDTO.getLongName() + groupDTO.getPublicDesc()
                + groupDTO.getDefaultLicense() + groupDTO.getType());
        UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        Group group = mapper.map(groupDTO, Group.class);
        final Group newGroup = groupManager.createGroup(group, user);
        return new StateToken(newGroup.getDefaultContent().getStateToken());
    }

    @Authenticated
    @Authorizated(accessTypeRequired = AccessType.ADMIN)
    @Transactional(type = TransactionType.READ_WRITE, rollbackOn = SerializableException.class)
    public void changeGroupWsTheme(final String userHash, final String groupShortName, final String theme)
            throws SerializableException {
        UserSession userSession = getUserSession();
        final User user = userSession.getUser();
        Group group = groupManager.findByShortName(groupShortName);
        groupManager.changeWsTheme(user, group, theme);
    }

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }

}
