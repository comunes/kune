package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
@Embeddable
public class AccessRights {
    private List<Group> admin;
    private List<Group> edit;
    private List<Group> view;

    public AccessRights() {
	this.admin = new ArrayList<Group>();
	this.edit = new ArrayList<Group>();
	this.view = new ArrayList<Group>();
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

}
