package org.ourproject.kune.platf.server.rpc;

import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.gwt.user.client.rpc.SerializableException;
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
    public String requestJoinGroup(final String hash, final String groupShortName) throws SerializableException {
	User user = session.getUser();
	Group group = groupManager.findByShortName(groupShortName);
	return socialNetworkManager.requestToJoin(user, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void AcceptJoinGroup(final String hash, final String groupToAcceptShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToAccept = groupManager.findByShortName(groupToAcceptShortName);
	socialNetworkManager.acceptJoinGroup(groupToAccept, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteMember(final String hash, final String groupToDeleleShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToDelete = groupManager.findByShortName(groupToDeleleShortName);
	socialNetworkManager.deleteMember(groupToDelete, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void denyJoinGroup(final String hash, final String groupToDenyShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToDenyJoin = groupManager.findByShortName(groupToDenyShortName);
	socialNetworkManager.denyJoinGroup(groupToDenyJoin, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void setCollabAsAdmin(final String hash, final String groupToSetAdminShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToSetAdmin = groupManager.findByShortName(groupToSetAdminShortName);
	socialNetworkManager.setCollabAsAdmin(groupToSetAdmin, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void setAdminAsCollab(final String hash, final String groupToSetCollabShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToSetCollab = groupManager.findByShortName(groupToSetCollabShortName);
	socialNetworkManager.setAdminAsCollab(groupToSetCollab, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void addAdminMember(final String hash, final String groupToAddShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
	socialNetworkManager.addGroupToAdmins(groupToAdd, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void addCollabMember(final String hash, final String groupToAddShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
	socialNetworkManager.addGroupToCollabs(groupToAdd, group);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE)
    public void addViewerMember(final String hash, final String groupToAddShortName, final String groupShortName)
	    throws SerializableException {
	Group group = groupManager.findByShortName(groupShortName);
	Group groupToAdd = groupManager.findByShortName(groupToAddShortName);
	socialNetworkManager.addGroupToViewers(groupToAdd, group);
    }
}
