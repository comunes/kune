package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.AlreadyGroupMemberException;
import org.ourproject.kune.platf.client.errors.LastAdminInGroupException;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupListMode;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.testhelper.ctx.DomainContext;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class SocialNetworkManagerTest extends PersistenceTest {
    @Inject
    private SocialNetworkManager socialNetworkManager;
    private Group group;
    private Group userGroup;
    private Group orphanedGroup;
    private User user;
    private User admin;
    private User otherUser;

    @Before
    public void init() {
        openTransaction();
        final DomainContext ctx = new DomainContext();
        ctx.createUsers("user1");
        ctx.createUsers("admin");
        ctx.createUsers("otheruser");
        ctx.createGroups("group1");
        ctx.createOrphanGroup("grouporph");
        user = ctx.getUser("user1");
        group = ctx.getGroup("group1");
        orphanedGroup = ctx.getGroup("grouporph");
        userGroup = user.getUserGroup();
        admin = ctx.getUser("admin");
        otherUser = ctx.getUser("otheruser");
    }

    @Test
    public void requestJoinAModeratedGroupAddUserGroupToPending() throws SerializableException {
        assertSocialNetworkIsEmpty();
        group.setAdmissionType(AdmissionType.Moderated);

        final String result = socialNetworkManager.requestToJoin(user, group);
        assertEquals(result, SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION);
        assertTrue(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
    }

    @Test
    public void requestJoinAOpenGroupAddUserGroupToEditors() throws SerializableException {
        assertSocialNetworkIsEmpty();
        group.setAdmissionType(AdmissionType.Open);

        final String result = socialNetworkManager.requestToJoin(user, group);
        assertEquals(result, SocialNetworkDTO.REQ_JOIN_ACEPTED);
        assertTrue(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NORMAL);
    }

    @Test
    public void requestJoinAOrphanedGroupAddUserGroupToAdmins() throws SerializableException {
        assertSocialNetworkIsEmpty();
        orphanedGroup.setAdmissionType(AdmissionType.Open);

        final String result = socialNetworkManager.requestToJoin(user, orphanedGroup);
        assertEquals(result, SocialNetworkDTO.REQ_JOIN_ACEPTED);
        assertTrue(orphanedGroup.getSocialNetwork().getAccessLists().getAdmins().getList().contains(userGroup));
        assertEquals(orphanedGroup.getSocialNetwork().getAccessLists().getAdmins().getMode(), GroupListMode.NORMAL);

        // FIXME Check change group type to PROJECT
    }

    @Test
    public void requestJoinTwiceAOrphanedGroupAddUserGroupToAdmins() throws SerializableException {
        assertSocialNetworkIsEmpty();
        orphanedGroup.setAdmissionType(AdmissionType.Open);

        final String result = socialNetworkManager.requestToJoin(user, orphanedGroup);
        assertEquals(result, SocialNetworkDTO.REQ_JOIN_ACEPTED);
        final String result2 = socialNetworkManager.requestToJoin(user, orphanedGroup);
        assertEquals(result2, SocialNetworkDTO.REQ_JOIN_ACEPTED);
        assertTrue(orphanedGroup.getSocialNetwork().getAccessLists().getAdmins().getList().contains(userGroup));
        assertEquals(1, orphanedGroup.getSocialNetwork().getAccessLists().getAdmins().getList().size());
        assertEquals(orphanedGroup.getSocialNetwork().getAccessLists().getAdmins().getMode(), GroupListMode.NORMAL);

        // FIXME Change group type to PROJECT
    }

    @Test
    public void requestJoinAClosedGroupDeny() throws SerializableException {
        assertSocialNetworkIsEmpty();
        group.setAdmissionType(AdmissionType.Closed);

        final String result = socialNetworkManager.requestToJoin(user, group);
        assertEquals(result, SocialNetworkDTO.REQ_JOIN_DENIED);
    }

    @Test(expected = RuntimeException.class)
    public void ilegalAdmissionType() throws SerializableException {
        group.setAdmissionType(null);

        socialNetworkManager.requestToJoin(user, group);
    }

    @Test
    public void requestToJoinTwiceDontDuplicatePending() throws SerializableException {
        assertSocialNetworkIsEmpty();
        group.setAdmissionType(AdmissionType.Moderated);

        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.requestToJoin(user, group);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 1);
    }

    @Test
    public void acceptJoinGroup() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);

        assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
        assertTrue(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 1);
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NORMAL);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
    }

    @Test
    public void denyJoinGroup() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.denyJoinGroup(admin, userGroup, group);

        assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
        assertFalse(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
    }

    @Test(expected = AccessViolationException.class)
    public void denyJoinGroupNotAdminFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.denyJoinGroup(otherUser, userGroup, group);
    }

    @Test
    public void deleteMember() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
        assertTrue(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
        socialNetworkManager.deleteMember(admin, userGroup, group);
        assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
        assertFalse(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NOBODY);
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
    }

    @Test(expected = LastAdminInGroupException.class)
    public void lastAdminUnjoinGroupFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.unJoinGroup(admin.getUserGroup(), group);
    }

    @Test(expected = AccessViolationException.class)
    public void notAdminTryDeleteMember() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
        socialNetworkManager.deleteMember(otherUser, userGroup, group);
    }

    @Test(expected = SerializableException.class)
    public void acceptJoinNotPendingGroupFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
    }

    @Test(expected = SerializableException.class)
    public void deleteNotMemberFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.deleteMember(admin, userGroup, group);
    }

    @Test(expected = SerializableException.class)
    public void denyNotPendingFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.denyJoinGroup(admin, userGroup, group);
    }

    @Test
    public void setCollabAsAdmin() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
        socialNetworkManager.setCollabAsAdmin(admin, userGroup, group);

        assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
        assertFalse(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
        assertTrue(group.getSocialNetwork().getAccessLists().getAdmins().getList().contains(userGroup));
        assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getList().size(), 2);
        assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getMode(), GroupListMode.NORMAL);
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NOBODY);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
    }

    @Test(expected = AccessViolationException.class)
    public void setCollabAsAdminNotAdminFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
        socialNetworkManager.setCollabAsAdmin(otherUser, userGroup, group);
    }

    @Test
    public void setAdminAsCollab() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
        socialNetworkManager.setCollabAsAdmin(admin, userGroup, group);
        socialNetworkManager.setAdminAsCollab(admin, userGroup, group);

        assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
        assertTrue(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
        assertFalse(group.getSocialNetwork().getAccessLists().getAdmins().getList().contains(userGroup));
        assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getList().size(), 1);
        assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getMode(), GroupListMode.NORMAL);
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 1);
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NORMAL);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
    }

    @Test(expected = AccessViolationException.class)
    public void setAdminAsCollabNotAdminFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
        socialNetworkManager.setCollabAsAdmin(otherUser, userGroup, group);
    }

    @Test(expected = SerializableException.class)
    public void setAdminNotCollabFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.setCollabAsAdmin(admin, userGroup, group);
    }

    @Test(expected = SerializableException.class)
    public void setAdminAnonMemberFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.setAdminAsCollab(admin, userGroup, group);
    }

    @Test(expected = SerializableException.class)
    public void addAdminNotAdminFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addGroupToAdmins(otherUser, userGroup, group);
    }

    @Test(expected = SerializableException.class)
    public void addCollabNotAdminFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addGroupToCollabs(otherUser, userGroup, group);
    }

    @Test(expected = SerializableException.class)
    public void addViewerNotAdminFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addGroupToViewers(otherUser, userGroup, group);
    }

    @Test(expected = AlreadyGroupMemberException.class)
    public void addAdminAsAdminFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.addGroupToAdmins(admin, admin.getUserGroup(), group);
    }

    @Test(expected = AlreadyGroupMemberException.class)
    public void addAdminAsCollabFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.addGroupToCollabs(admin, admin.getUserGroup(), group);
    }

    @Test(expected = AlreadyGroupMemberException.class)
    public void addAdminAsViewerFails() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.addGroupToViewers(admin, admin.getUserGroup(), group);
    }

    @Test
    public void addPendingAsCollabDirectly() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addGroupToCollabs(admin, userGroup, group);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
    }

    @Test
    public void addPendingAsCollabDirectlyAsAdmin() throws SerializableException {
        assertSocialNetworkIsEmpty();
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addGroupToAdmins(admin, userGroup, group);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
    }

    private void assertSocialNetworkIsEmpty() {
        assertEquals(group.getSocialNetwork().getAccessLists().getAdmins().getList().size(), 0);
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }
}
