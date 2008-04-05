
package org.ourproject.kune.platf.server.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.AlreadyGroupMemberException;
import org.ourproject.kune.platf.client.errors.AlreadyUserMemberException;
import org.ourproject.kune.platf.client.errors.LastAdminInGroupException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.ParticipationData;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupListMode;
import org.ourproject.kune.platf.server.domain.GroupType;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;
import org.ourproject.kune.platf.server.users.Link;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SocialNetworkManagerDefault extends DefaultManager<SocialNetwork, Long> implements SocialNetworkManager {

    private final Group finder;

    @Inject
    public SocialNetworkManagerDefault(final Provider<EntityManager> provider, final Group finder) {
        super(provider, SocialNetwork.class);
        this.finder = finder;
    }

    public void addAdmin(final User user, final Group group) {
        SocialNetwork sn = group.getSocialNetwork();
        sn.addAdmin(user.getUserGroup());
    }

    public void addGroupToAdmins(final User userLogged, final Group group, final Group inGroup)
            throws SerializableException {
        checkGroupAddingToSelf(group, inGroup);
        SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        checkGroupIsNotAlreadyAMember(group, sn);
        sn.addAdmin(group);
        if (sn.isPendingCollab(group)) {
            sn.removePendingCollaborator(group);
        }
    }

    public void addGroupToCollabs(final User userLogged, final Group group, final Group inGroup)
            throws SerializableException {
        checkGroupAddingToSelf(group, inGroup);
        SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        checkGroupIsNotAlreadyAMember(group, sn);
        sn.addCollaborator(group);
        if (sn.isPendingCollab(group)) {
            sn.removePendingCollaborator(group);
        }
    }

    public void addGroupToViewers(final User userLogged, final Group group, final Group inGroup)
            throws SerializableException {
        checkGroupAddingToSelf(group, inGroup);
        SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        checkGroupIsNotAlreadyAMember(group, sn);
        sn.addViewer(group);
        if (sn.isPendingCollab(group)) {
            sn.removePendingCollaborator(group);
        }
    }

    public String requestToJoin(final User user, final Group inGroup) throws SerializableException,
            UserMustBeLoggedException {
        SocialNetwork sn = inGroup.getSocialNetwork();
        if (!User.isKnownUser(user)) {
            throw new UserMustBeLoggedException();
        }
        AdmissionType admissionType = inGroup.getAdmissionType();
        if (admissionType == null) {
            throw new RuntimeException();
        }
        Group userGroup = user.getUserGroup();
        checkGroupIsNotAlreadyAMember(userGroup, sn);
        if (isModerated(admissionType)) {
            sn.addPendingCollaborator(userGroup);
            return SocialNetworkDTO.REQ_JOIN_WAITING_MODERATION;
        } else if (isOpen(admissionType)) {
            if (inGroup.getType().equals(GroupType.ORPHANED_PROJECT)) {
                sn.addAdmin(userGroup);
                inGroup.setType(GroupType.PROJECT);
                inGroup.setAdmissionType(AdmissionType.Moderated);
                persist(inGroup, Group.class);
            } else {
                sn.addCollaborator(userGroup);
            }
            return SocialNetworkDTO.REQ_JOIN_ACEPTED;
        } else if (isClosed(admissionType)) {
            return SocialNetworkDTO.REQ_JOIN_DENIED;
        } else {
            throw new SerializableException("State not expected in SocialNetworkManagerDefault class");
        }
    }

    public void acceptJoinGroup(final User userLogged, final Group group, final Group inGroup)
            throws SerializableException, AccessViolationException {
        SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        List<Group> pendingCollabs = sn.getPendingCollaborators().getList();
        if (pendingCollabs.contains(group)) {
            sn.addCollaborator(group);
            sn.removePendingCollaborator(group);
        } else {
            throw new SerializableException("User is not a pending collaborator");
        }
    }

    public void deleteMember(final User userLogged, final Group group, final Group inGroup)
            throws SerializableException, AccessViolationException {
        SocialNetwork sn = inGroup.getSocialNetwork();

        checkUserLoggedIsAdmin(userLogged, sn);
        unJoinGroup(group, inGroup);
    }

    public void unJoinGroup(final Group groupToUnJoin, final Group inGroup) throws SerializableException {
        SocialNetwork sn = inGroup.getSocialNetwork();

        if (sn.isAdmin(groupToUnJoin)) {
            if (sn.getAccessLists().getAdmins().getList().size() == 1) {
                throw new LastAdminInGroupException();
            }
            sn.removeAdmin(groupToUnJoin);
        } else if (sn.isCollab(groupToUnJoin)) {
            sn.removeCollaborator(groupToUnJoin);
        } else {
            throw new SerializableException("Person/Group is not a collaborator");
        }
    }

    public void denyJoinGroup(final User userLogged, final Group group, final Group inGroup)
            throws SerializableException {
        SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        List<Group> pendingCollabs = sn.getPendingCollaborators().getList();
        if (pendingCollabs.contains(group)) {
            sn.removePendingCollaborator(group);
        } else {
            throw new SerializableException("Person/Group is not a pending collaborator");
        }
    }

    public void setCollabAsAdmin(final User userLogged, final Group group, final Group inGroup)
            throws SerializableException {
        SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        if (sn.isCollab(group)) {
            sn.removeCollaborator(group);
            sn.addAdmin(group);
        } else {
            throw new SerializableException("Person/Group is not a collaborator");
        }
    }

    public void setAdminAsCollab(final User userLogged, final Group group, final Group inGroup)
            throws SerializableException {
        SocialNetwork sn = inGroup.getSocialNetwork();
        checkUserLoggedIsAdmin(userLogged, sn);
        if (sn.isAdmin(group)) {
            if (sn.getAccessLists().getAdmins().getList().size() == 1) {
                throw new LastAdminInGroupException();
            }
            sn.removeAdmin(group);
            sn.addCollaborator(group);
        } else {
            throw new SerializableException("Person/Group is not an admin");
        }
    }

    public SocialNetwork find(final User user, final Group group) throws AccessViolationException {
        SocialNetwork sn = group.getSocialNetwork();
        if (!sn.getAccessLists().getViewers().includes(user.getUserGroup())) {
            throw new AccessViolationException();
        }
        return sn;
    }

    public ParticipationData findParticipation(final User user, final Group group) throws AccessViolationException {
        find(user, group); // check access
        Long groupId = group.getId();
        List<Group> adminInGroups = finder.findAdminInGroups(groupId);
        List<Group> collabInGroups = finder.findCollabInGroups(groupId);
        List<Link> groupsIsAdmin = new ArrayList<Link>();
        List<Link> groupsIsCollab = new ArrayList<Link>();
        Iterator<Group> iter = adminInGroups.iterator();
        while (iter.hasNext()) {
            Group g = iter.next();
            if (group.getId() != g.getId()) {
                // Don't self participation of group in same group
                groupsIsAdmin
                        .add(new Link(g.getShortName(), g.getLongName(), "", g.getDefaultContent().getStateToken()));
            }

        }
        iter = collabInGroups.iterator();
        while (iter.hasNext()) {
            Group g = iter.next();
            if (group.getId() != g.getId()) {
                groupsIsCollab.add(new Link(g.getShortName(), g.getLongName(), "", g.getDefaultContent()
                        .getStateToken()));
            }
        }

        return new ParticipationData(groupsIsAdmin, groupsIsCollab);
    }

    private boolean isClosed(final AdmissionType admissionType) {
        return admissionType.equals(AdmissionType.Closed);
    }

    private boolean isOpen(final AdmissionType admissionType) {
        return admissionType.equals(AdmissionType.Open);
    }

    private boolean isModerated(final AdmissionType admissionType) {
        return admissionType.equals(AdmissionType.Moderated);
    }

    private void checkUserLoggedIsAdmin(final User userLogged, final SocialNetwork sn) throws AccessViolationException {
        if (!sn.isAdmin(userLogged.getUserGroup())) {
            throw new AccessViolationException();
        }
    }

    private void checkGroupIsNotAlreadyAMember(final Group group, final SocialNetwork sn) throws SerializableException {
        if (sn.isAdmin(group) || sn.isCollab(group) || sn.isViewer(group) && notEveryOneCanView(sn)) {
            throwGroupMemberException(group);
        }
    }

    private void throwGroupMemberException(final Group group) throws SerializableException {
        if (group.getType().equals(GroupType.PERSONAL)) {
            throw new AlreadyUserMemberException();
        } else {
            throw new AlreadyGroupMemberException();
        }
    }

    private void checkGroupAddingToSelf(final Group group, final Group inGroup) throws SerializableException {
        if (group.equals(inGroup)) {
            throwGroupMemberException(group);
        }
    }

    private boolean notEveryOneCanView(final SocialNetwork sn) {
        return !sn.getAccessLists().getViewers().getMode().equals(GroupListMode.EVERYONE);
    }

}
