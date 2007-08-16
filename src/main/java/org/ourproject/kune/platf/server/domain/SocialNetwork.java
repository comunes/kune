package org.ourproject.kune.platf.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "social_networks")
public class SocialNetwork {
    @Id
    @GeneratedValue
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    AccessLists accessList;

    @OneToOne(cascade = CascadeType.ALL)
    GroupList pendingCollaborators;

    public SocialNetwork() {
	accessList = new AccessLists();
	pendingCollaborators = new GroupList();
    }

    public Long getId() {
	return id;
    }

    public GroupList getPendingCollaborators() {
	return pendingCollaborators;
    }

    public void setPendingCollaborators(final GroupList pendingCollaborators) {
	this.pendingCollaborators = pendingCollaborators;
    }

    public void setAccessList(final AccessLists accessList) {
	this.accessList = accessList;
    }

    public void setId(final Long id) {
	this.id = id;
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
