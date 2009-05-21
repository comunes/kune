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
package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.dto.SocialNetworkRequestResult;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.AlreadyGroupMemberException;
import org.ourproject.kune.platf.client.errors.AlreadyUserMemberException;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.LastAdminInGroupException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.ServerException;
import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.access.AccessRightsService;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupListMode;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.SocialNetworkData;
import org.ourproject.kune.platf.server.domain.SocialNetworkVisibility;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.domain.UserBuddiesVisibility;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.sn.ParticipationData;
import org.ourproject.kune.platf.server.sn.UserBuddiesData;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SocialNetworkManagerDefault extends DefaultManager<SocialNetwork, Long> implements SocialNetworkManager {

    private final Group finder;
    private final AccessRightsService accessRightsService;
    private final UserManager userManager;

    @Inject
    public SocialNetworkManagerDefault(final Provider<EntityManager> provider, final Group finder,
            final AccessRightsService accessRightsService, final UserManager userManager) {
        super(provider, SocialNetwork.class);
        this.finder = finder;
        this.accessRightsService = accessRightsService;
        this.userManager = userManager;
    }

    public void acceptJoinGroup(final User userLogged, final Group group, final Group inGroup) throws DefaultException,
            AccessViolationException {
        final SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        final List<Group> pendingCollabs = sn.getPendingCollaborators().getList();
        if (pendingCollabs.contains(group)) {
            sn.addCollaborator(group);
            sn.removePendingCollaborator(group);
        } else {
            throw new DefaultException("User is not a pending collaborator");
        }
    }

    public void addGroupToAdmins(final User userLogged, final Group group, final Group inGroup) throws DefaultException {
        checkGroupAddingToSelf(group, inGroup);
        final SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        checkGroupIsNotAlreadyAMember(group, sn);
        sn.addAdmin(group);
        if (sn.isPendingCollab(group)) {
            sn.removePendingCollaborator(group);
        }
    }

    public void addGroupToCollabs(final User userLogged, final Group group, final Group inGroup)
            throws DefaultException {
        checkGroupAddingToSelf(group, inGroup);
        final SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        checkGroupIsNotAlreadyAMember(group, sn);
        sn.addCollaborator(group);
        if (sn.isPendingCollab(group)) {
            sn.removePendingCollaborator(group);
        }
    }

    public void addGroupToViewers(final User userLogged, final Group group, final Group inGroup)
            throws DefaultException {
        checkGroupAddingToSelf(group, inGroup);
        final SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        checkGroupIsNotAlreadyAMember(group, sn);
        sn.addViewer(group);
        if (sn.isPendingCollab(group)) {
            sn.removePendingCollaborator(group);
        }
    }

    public void deleteMember(final User userLogged, final Group group, final Group inGroup) throws DefaultException,
            AccessViolationException {
        final SocialNetwork sn = inGroup.getSocialNetwork();

        checkUserLoggedIsAdmin(userLogged, sn);
        unJoinGroup(group, inGroup);
    }

    public void denyJoinGroup(final User userLogged, final Group group, final Group inGroup) throws DefaultException {
        final SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        final List<Group> pendingCollabs = sn.getPendingCollaborators().getList();
        if (pendingCollabs.contains(group)) {
            sn.removePendingCollaborator(group);
        } else {
            throw new DefaultException("Person/Group is not a pending collaborator");
        }
    }

    public ParticipationData findParticipation(final User userLogged, final Group group)
            throws AccessViolationException {
        get(userLogged, group); // check access
        final Long groupId = group.getId();
        final List<Group> adminInGroups = finder.findAdminInGroups(groupId);
        // Don't show self user group
        if (group.isPersonal()) {
            adminInGroups.remove(group);
        }
        // adminInGroups.remove(userLogged.getUserGroup());
        final List<Group> collabInGroups = finder.findCollabInGroups(groupId);
        return new ParticipationData(adminInGroups, collabInGroups);
    }

    public SocialNetwork get(final User petitioner, final Group group) throws AccessViolationException {
        final SocialNetwork sn = group.getSocialNetwork();
        if (!sn.getAccessLists().getViewers().includes(petitioner.getUserGroup())) {
            throw new AccessViolationException();
        }
        return sn;
    }

    public SocialNetworkData getSocialNetworkData(final User userLogged, final Group group) {
        SocialNetworkData socialNetData = new SocialNetworkData();
        socialNetData.setGroupMembers(get(userLogged, group));
        AccessRights groupRights = accessRightsService.get(userLogged, group.getAccessLists());
        socialNetData.setGroupRights(groupRights);
        socialNetData.setUserParticipation(findParticipation(userLogged, group));
        socialNetData.setGroupMembers(get(userLogged, group));
        if (group.isPersonal()) {
            UserBuddiesData userBuddies = userManager.getUserBuddies(group.getShortName());
            User userGroup = userManager.findByShortname(group.getShortName());
            socialNetData.setUserBuddies(userBuddies);
            UserBuddiesVisibility buddiesVisibility = userGroup.getBuddiesVisibility();
            socialNetData.setIsBuddiesVisible(true);
            switch (buddiesVisibility) {
            case anyone:
                break;
            case onlyyou:
                if (userLogged == User.UNKNOWN_USER || !userLogged.getUserGroup().equals(group)) {
                    socialNetData.setIsBuddiesVisible(false);
                    socialNetData.setUserBuddies(UserBuddiesData.EMPTY);
                }
                break;
            case yourbuddies:
                if (userLogged != userGroup && !userBuddies.contains(userLogged.getShortName())) {
                    socialNetData.setIsBuddiesVisible(false);
                    socialNetData.setUserBuddies(UserBuddiesData.EMPTY);
                }
                break;
            }
            socialNetData.setUserBuddiesVisibility(buddiesVisibility);
        } else {
            SocialNetworkVisibility visibility = group.getSocialNetwork().getVisibility();
            socialNetData.setIsMembersVisible(true);
            switch (visibility) {
            case anyone:
                break;
            case onlyadmins:
                if (!groupRights.isAdministrable()) {
                    socialNetData.setIsMembersVisible(false);
                    socialNetData.setGroupMembers(SocialNetwork.EMPTY);
                }
                break;
            case onlymembers:
                if (!groupRights.isEditable()) {
                    socialNetData.setIsMembersVisible(false);
                    socialNetData.setGroupMembers(SocialNetwork.EMPTY);
                }
                break;
            }
            socialNetData.setSocialNetworkVisibility(visibility);
            socialNetData.setUserBuddies(UserBuddiesData.EMPTY);
        }
        return socialNetData;
    }

    public SocialNetworkRequestResult requestToJoin(final User userLogged, final Group inGroup)
            throws DefaultException, UserMustBeLoggedException {
        final SocialNetwork sn = inGroup.getSocialNetwork();
        if (!User.isKnownUser(userLogged)) {
            throw new UserMustBeLoggedException();
        }
        final AdmissionType admissionType = inGroup.getAdmissionType();
        if (admissionType == null) {
            throw new ServerException("No admissionType");
        }
        final Group userGroup = userLogged.getUserGroup();
        checkGroupIsNotAlreadyAMember(userGroup, sn);
        if (isModerated(admissionType)) {
            sn.addPendingCollaborator(userGroup);
            return SocialNetworkRequestResult.moderated;
        } else if (isOpen(admissionType)) {
            if (inGroup.getGroupType().equals(GroupType.ORPHANED_PROJECT)) {
                sn.addAdmin(userGroup);
                inGroup.setGroupType(GroupType.PROJECT);
                inGroup.setAdmissionType(AdmissionType.Moderated);
                persist(inGroup, Group.class);
            } else {
                sn.addCollaborator(userGroup);
            }
            return SocialNetworkRequestResult.accepted;
        } else if (isClosed(admissionType)) {
            return SocialNetworkRequestResult.denied;
        } else {
            throw new DefaultException("State not expected in SocialNetworkManagerDefault class");
        }
    }

    public void setAdminAsCollab(final User userLogged, final Group group, final Group inGroup) throws DefaultException {
        final SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        if (sn.isAdmin(group)) {
            if (sn.getAccessLists().getAdmins().getList().size() == 1) {
                throw new LastAdminInGroupException();
            }
            sn.removeAdmin(group);
            sn.addCollaborator(group);
        } else {
            throw new DefaultException("Person/Group is not an admin");
        }
    }

    public void setCollabAsAdmin(final User userLogged, final Group group, final Group inGroup) throws DefaultException {
        final SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        if (sn.isCollab(group)) {
            sn.removeCollaborator(group);
            sn.addAdmin(group);
        } else {
            throw new DefaultException("Person/Group is not a collaborator");
        }
    }

    public void unJoinGroup(final Group groupToUnJoin, final Group inGroup) throws DefaultException {
        final SocialNetwork sn = inGroup.getSocialNetwork();

        if (sn.isAdmin(groupToUnJoin)) {
            if (sn.getAccessLists().getAdmins().getList().size() == 1) {
                if (sn.getAccessLists().getEditors().getList().size() > 0) {
                    throw new LastAdminInGroupException();
                } else {
                    inGroup.setGroupType(GroupType.ORPHANED_PROJECT);
                    inGroup.setAdmissionType(AdmissionType.Open);
                }
            }
            sn.removeAdmin(groupToUnJoin);
        } else if (sn.isCollab(groupToUnJoin)) {
            sn.removeCollaborator(groupToUnJoin);
        } else {
            throw new DefaultException("Person/Group is not a collaborator");
        }
    }

    void addAdmin(final User newAdmin, final Group group) {
        final SocialNetwork sn = group.getSocialNetwork();
        sn.addAdmin(newAdmin.getUserGroup());
    }

    private void checkGroupAddingToSelf(final Group group, final Group inGroup) throws DefaultException {
        if (group.equals(inGroup)) {
            throwGroupMemberException(group);
        }
    }

    private void checkGroupIsNotAlreadyAMember(final Group group, final SocialNetwork sn) throws DefaultException {
        if (sn.isAdmin(group) || sn.isCollab(group) || sn.isViewer(group) && notEveryOneCanView(sn)) {
            throwGroupMemberException(group);
        }
    }

    private void checkUserLoggedIsAdmin(final User userLogged, final SocialNetwork sn) throws AccessViolationException {
        if (!accessRightsService.get(userLogged, sn.getAccessLists()).isAdministrable()) {
            throw new AccessViolationException();
        }
    }

    private boolean isClosed(final AdmissionType admissionType) {
        return admissionType.equals(AdmissionType.Closed);
    }

    private boolean isModerated(final AdmissionType admissionType) {
        return admissionType.equals(AdmissionType.Moderated);
    }

    private boolean isOpen(final AdmissionType admissionType) {
        return admissionType.equals(AdmissionType.Open);
    }

    private boolean notEveryOneCanView(final SocialNetwork sn) {
        return !sn.getAccessLists().getViewers().getMode().equals(GroupListMode.EVERYONE);
    }

    private void throwGroupMemberException(final Group group) throws DefaultException {
        if (group.isPersonal()) {
            throw new AlreadyUserMemberException();
        } else {
            throw new AlreadyGroupMemberException();
        }
    }

}
