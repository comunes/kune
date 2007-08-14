package org.ourproject.kune.platf.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class SocialNetwork {

    @OneToOne(cascade = CascadeType.ALL)
    GroupList admins;

    @OneToOne(cascade = CascadeType.ALL)
    GroupList collaborators;

    @OneToOne(cascade = CascadeType.ALL)
    GroupList viewer;

    @OneToOne(cascade = CascadeType.ALL)
    GroupList pendingCollaborators;

    public SocialNetwork() {
	admins = new GroupList();
	collaborators = new GroupList();
	viewer = new GroupList();
	pendingCollaborators = new GroupList();
    }

    public SocialNetwork(final GroupList admins, final GroupList collaborators, final GroupList viewer,
	    final GroupList pendingCollab) {
	this.admins = admins;
	this.collaborators = collaborators;
	this.viewer = viewer;
	this.pendingCollaborators = pendingCollab;
    }

    public void addAdmin(final Group group) {
	admins.add(group);
    }

    public void addCollaborator(final Group group) {
	collaborators.add(group);
    }

    public void addViewer(final Group group) {
	viewer.add(group);
    }

    public void addPendingCollaborator(final Group group) {
	pendingCollaborators.add(group);
    }

    public GroupList getAdmins() {
	return admins;
    }

    public void setAdmins(final GroupList admins) {
	this.admins = admins;
    }

    public GroupList getCollaborators() {
	return collaborators;
    }

    public void setCollaborators(final GroupList collaborators) {
	this.collaborators = collaborators;
    }

    public GroupList getViewer() {
	return viewer;
    }

    public void setViewer(final GroupList viewer) {
	this.viewer = viewer;
    }

    public GroupList getPendingCollaborators() {
	return pendingCollaborators;
    }

    public void setPendingCollaborators(final GroupList pendingCollaborators) {
	this.pendingCollaborators = pendingCollaborators;
    }

    public AccessLists getDefaultAccessLists() {
	return new AccessLists(admins.getList(), collaborators.getList(), viewer.getList());
    }

}
