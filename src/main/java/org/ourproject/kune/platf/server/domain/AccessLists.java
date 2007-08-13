package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class AccessLists {
    @OneToMany
    private List<Group> admin;
    @OneToMany
    private List<Group> edit;
    @OneToMany
    private List<Group> view;

    public AccessLists() {
	this.admin = new ArrayList<Group>();
	this.edit = new ArrayList<Group>();
	this.view = new ArrayList<Group>();
    }

    public AccessLists(final List<Group> admins, final List<Group> collaborators, final List<Group> viewer) {
	this.admin = admins;
	this.edit = collaborators;
	this.view = viewer;
    }

    public List<Group> getAdmin() {
	return admin;
    }

    public void setAdmin(final ArrayList<Group> admin) {
	this.admin = admin;
    }

    public List<Group> getEdit() {
	return edit;
    }

    public void setEdit(final ArrayList<Group> edit) {
	this.edit = edit;
    }

    public List<Group> getView() {
	return view;
    }

    public void setView(final ArrayList<Group> view) {
	this.view = view;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((admin == null) ? 0 : admin.hashCode());
	result = prime * result + ((edit == null) ? 0 : edit.hashCode());
	result = prime * result + ((view == null) ? 0 : view.hashCode());
	return result;
    }

    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final AccessLists other = (AccessLists) obj;
	if (admin == null) {
	    if (other.admin != null) {
		return false;
	    }
	} else if (!admin.equals(other.admin)) {
	    return false;
	}
	if (edit == null) {
	    if (other.edit != null) {
		return false;
	    }
	} else if (!edit.equals(other.edit)) {
	    return false;
	}
	if (view == null) {
	    if (other.view != null) {
		return false;
	    }
	} else if (!view.equals(other.view)) {
	    return false;
	}
	return true;
    }

}
