package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class SocialNetwork {

    @OneToMany
    List<Group> admins;

    @OneToMany
    List<Group> collaborators;

    @OneToMany
    List<Group> viewer;

    @OneToMany
    List<Group> pendingCollaborators;

    public SocialNetwork() {
	admins = new ArrayList<Group>();
	collaborators = new ArrayList<Group>();
	viewer = new ArrayList<Group>();
	pendingCollaborators = new ArrayList<Group>();

    }

    public SocialNetwork(final ArrayList<Group> admins, final ArrayList<Group> collaborators,
	    final ArrayList<Group> viewer, final ArrayList<Group> pendingCollaborators) {
	this.admins = admins;
	this.collaborators = collaborators;
	this.viewer = viewer;
	this.pendingCollaborators = pendingCollaborators;
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

    public List<Group> getAdmins() {
	return admins;
    }

    public void setAdmins(final List<Group> admins) {
	this.admins = admins;
    }

    public List<Group> getCollaborators() {
	return collaborators;
    }

    public void setCollaborators(final List<Group> collaborators) {
	this.collaborators = collaborators;
    }

    public List<Group> getViewer() {
	return viewer;
    }

    public void setViewer(final List<Group> viewer) {
	this.viewer = viewer;
    }

    public List<Group> getPendingCollaborators() {
	return pendingCollaborators;
    }

    public void setPendingCollaborators(final List<Group> pendingCollaborators) {
	this.pendingCollaborators = pendingCollaborators;
    }

    public AccessLists getDefaultAccessLists() {
	return new AccessLists(admins, collaborators, viewer);
    }

}
