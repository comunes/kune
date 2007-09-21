package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.SocialNetworkManagerDefault;
import org.ourproject.kune.testhelper.ctx.DomainContext;

public class SocialNetworkManagerTest {
    private SocialNetworkManagerDefault socialNetworkManager;
    private Group group;
    private User user;
    private Group userGroup;

    @Before
    public void init() {
	socialNetworkManager = new SocialNetworkManagerDefault(null);
	final DomainContext ctx = new DomainContext();
	ctx.createUsers("user1");
	ctx.createGroups("group1");
	user = ctx.getUser("user1");
	group = ctx.getGroup("group1");
	userGroup = user.getUserGroup();
    }

    @Test
    public void requestJoinAModeratedGroupAddUserGroupToPending() {
	group.setAdmissionType(AdmissionType.Moderated);

	final String result = socialNetworkManager.requestToJoin(group, user);
	assertEquals(result, SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION);
	group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup);
    }

    @Test
    public void requestJoinAOpenGroupAddUserGroupToEditors() {
	group.setAdmissionType(AdmissionType.Open);

	final String result = socialNetworkManager.requestToJoin(group, user);
	assertEquals(result, SocialNetworkDTO.REQ_JOIN_ACEPTED);
	group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup);
    }

    @Test
    public void requestJoinAClosedGroupDeny() {
	group.setAdmissionType(AdmissionType.Closed);

	final String result = socialNetworkManager.requestToJoin(group, user);
	assertEquals(result, SocialNetworkDTO.REQ_JOIN_DENIED);
    }

    @Test(expected = RuntimeException.class)
    public void ilegalAdmissionType() {
	group.setAdmissionType(null);

	socialNetworkManager.requestToJoin(group, user);
    }

    @Test
    public void requestToJoinTwiceDontDuplicatePending() {
	group.setAdmissionType(AdmissionType.Moderated);

	socialNetworkManager.requestToJoin(group, user);
	socialNetworkManager.requestToJoin(group, user);
	group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup);
	assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 1);
    }

}
