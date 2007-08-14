package org.ourproject.kune.platf.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class AccessLists {
    @OneToOne(cascade = CascadeType.ALL)
    protected GroupList admins;
    @OneToOne(cascade = CascadeType.ALL)
    protected GroupList editors;
    @OneToOne(cascade = CascadeType.ALL)
    protected GroupList viewers;

    public AccessLists() {
	this.admins = new GroupList();
	this.editors = new GroupList();
	this.viewers = new GroupList();
    }

    public void addAdmin(final Group group) {
	admins.add(group);
    }

    public void addEditor(final Group group) {
	editors.add(group);
    }

    public void addViewer(final Group group) {
	viewers.add(group);
    }

    public GroupList getAdmins() {
	return admins;
    }

    public GroupList getEditors() {
	return editors;
    }

    public GroupList getViewers() {
	return viewers;
    }

}
