package org.ourproject.kune.platf.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class SocialNetwork {
    AccessLists accessList;

    @OneToOne(cascade = CascadeType.ALL)
    GroupList pendingCollaborators;

    public SocialNetwork() {
	accessList = new AccessLists();
	pendingCollaborators = new GroupList();
    }

    public void addAdmin(final Group group) {
	accessList.addAdmin(group);
    }

    public void addCollaborator(final Group group) {
	accessList.addEditor(group);
    }

    public void addViewer(final Group group) {
	accessList.addViewer(group);
    }

    public void addPendingCollaborator(final Group group) {
	pendingCollaborators.add(group);
    }

    public AccessLists getAccessList() {
	return accessList;
    }

}
