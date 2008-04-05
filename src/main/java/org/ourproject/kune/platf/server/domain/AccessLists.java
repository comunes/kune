
package org.ourproject.kune.platf.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.ourproject.kune.platf.server.access.AccessRol;

@Entity
@Table(name = "access_lists")
public class AccessLists {
    @Id
    @GeneratedValue
    private Long id;

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

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
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

    public GroupList getList(final AccessRol rol) {
	if (rol == AccessRol.Administrator) {
	    return getAdmins();
	} else if (rol == AccessRol.Editor) {
	    return getEditors();
	} else {
	    return getViewers();
	}
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

    public void removeAdmin(final Group group) {
	admins.remove(group);
    }

    public void removeEditor(final Group group) {
	editors.remove(group);
    }

    public void removeViewer(final Group group) {
	viewers.remove(group);
    }
}
