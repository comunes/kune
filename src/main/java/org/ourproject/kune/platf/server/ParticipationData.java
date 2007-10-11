package org.ourproject.kune.platf.server;

import java.util.List;

import org.ourproject.kune.platf.server.users.Link;

public class ParticipationData {
    private List<Link> groupsIsAdmin;
    private List<Link> groupsIsCollab;

    public ParticipationData() {
	this(null, null);
    }

    public ParticipationData(final List<Link> groupsIsAdmin, final List<Link> groupsIsCollab) {
	this.groupsIsAdmin = groupsIsAdmin;
	this.groupsIsCollab = groupsIsCollab;
    }

    public List<Link> getGroupsIsAdmin() {
	return groupsIsAdmin;
    }

    public void setGroupsIsAdmin(final List<Link> groupsIsAdmin) {
	this.groupsIsAdmin = groupsIsAdmin;
    }

    public List<Link> getGroupsIsCollab() {
	return groupsIsCollab;
    }

    public void setGroupsIsCollab(final List<Link> groupsIsCollab) {
	this.groupsIsCollab = groupsIsCollab;
    }

}
