package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class AccessList {
    @OneToMany
    private List<Group> admin;
    @OneToMany
    private List<Group> edit;
    @OneToMany
    private List<Group> view;

    public AccessList() {
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
