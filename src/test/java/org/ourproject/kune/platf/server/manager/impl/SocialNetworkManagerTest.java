package org.ourproject.kune.platf.server.manager.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.AlreadyGroupMemberException;
import cc.kune.core.shared.domain.GroupListMode;

import com.google.inject.Inject;

public class SocialNetworkManagerTest extends AbstractSocialNetworkManagerTest {
    @Inject
    protected SocialNetworkManagerDefault socialNetworkManager;

    @Test
    public void acceptJoinGroup() throws Exception {
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);

        assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
        assertTrue(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 1);
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getMode(), GroupListMode.NORMAL);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
        closeTransaction();
    }

    @Test(expected = Exception.class)
    public void acceptJoinNotPendingGroupFails() throws Exception {
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.acceptJoinGroup(admin, userGroup, group);
    }

    @Test(expected = AlreadyGroupMemberException.class)
    public void addAdminAsAdminFails() throws Exception {
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.addGroupToAdmins(admin, admin.getUserGroup(), group);
    }

    @Test(expected = AlreadyGroupMemberException.class)
    public void addAdminAsCollabFails() throws Exception {
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.addGroupToCollabs(admin, admin.getUserGroup(), group);
    }

    @Test(expected = AlreadyGroupMemberException.class)
    public void addAdminAsViewerFails() throws Exception {
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.addGroupToViewers(admin, admin.getUserGroup(), group);
    }

    @Test(expected = Exception.class)
    public void addAdminNotAdminFails() throws Exception {
        socialNetworkManager.addGroupToAdmins(otherUser, userGroup, group);
    }

    @Test(expected = Exception.class)
    public void addCollabNotAdminFails() throws Exception {
        socialNetworkManager.addGroupToCollabs(otherUser, userGroup, group);
    }

    @Test
    public void addPendingAsCollabDirectly() throws Exception {
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addGroupToCollabs(admin, userGroup, group);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
        closeTransaction();
    }

    @Test
    public void addPendingAsCollabDirectlyAsAdmin() throws Exception {
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addGroupToAdmins(admin, userGroup, group);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
        closeTransaction();
    }

    @Test(expected = Exception.class)
    public void addViewerNotAdminFails() throws Exception {
        socialNetworkManager.addGroupToViewers(otherUser, userGroup, group);
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            rollbackTransaction();
        }
    }

    @Test
    public void deleteMember() throws Exception {
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
        closeTransaction();
    }

    @Test(expected = Exception.class)
    public void deleteNotMemberFails() throws Exception {
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.deleteMember(admin, userGroup, group);
    }

    @Test
    public void denyJoinGroup() throws Exception {
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.denyJoinGroup(admin, userGroup, group);

        assertFalse(group.getSocialNetwork().getPendingCollaborators().getList().contains(userGroup));
        assertFalse(group.getSocialNetwork().getAccessLists().getEditors().getList().contains(userGroup));
        assertEquals(group.getSocialNetwork().getAccessLists().getEditors().getList().size(), 0);
        assertEquals(group.getSocialNetwork().getPendingCollaborators().getList().size(), 0);
        closeTransaction();
    }

    @Test(expected = AccessViolationException.class)
    public void denyJoinGroupNotAdminFails() throws Exception {
        socialNetworkManager.requestToJoin(user, group);
        socialNetworkManager.denyJoinGroup(otherUser, userGroup, group);
    }

    @Test(expected = Exception.class)
    public void denyNotPendingFails() throws Exception {
        socialNetworkManager.addAdmin(admin, group);
        socialNetworkManager.denyJoinGroup(admin, userGroup, group);
    }

    @Test(expected = RuntimeException.class)
    public void ilegalAdmissionType() throws Exception {
        group.setAdmissionType(null);
        socialNetworkManager.requestToJoin(user, group);
    }

}
