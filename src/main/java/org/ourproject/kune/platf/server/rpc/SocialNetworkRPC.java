package org.ourproject.kune.platf.server.rpc;

import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class SocialNetworkRPC implements SocialNetworkService, RPC {

    private final UserSession session;
    private final GroupManager groupManager;
    private final SocialNetworkManager socialNetworkManager;

    @Inject
    public SocialNetworkRPC(final UserSession session, final GroupManager groupManager,
	    final SocialNetworkManager socialNetworkManager, final Mapper mapper) {
	this.session = session;
	this.groupManager = groupManager;
	this.socialNetworkManager = socialNetworkManager;
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public String requestJoinGroup(final String hash, final String groupShortName) {
	User user = session.getUser();
	Group group = groupManager.findByShortName(groupShortName);
	return socialNetworkManager.requestToJoin(group, user);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void AcceptJoinGroup(final String hash, final String groupShortName) {
	User user = session.getUser();
	Group group = groupManager.findByShortName(groupShortName);
	socialNetworkManager.acceptJoinGroup(group, user);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteMember(final String hash, final String groupShortName) {
	User user = session.getUser();
	Group group = groupManager.findByShortName(groupShortName);
	socialNetworkManager.deleteMember(group, user);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void denyJoinGroup(final String hash, final String groupShortName) {
	User user = session.getUser();
	Group group = groupManager.findByShortName(groupShortName);
	socialNetworkManager.denyJoinGroup(group, user);
    }

}
