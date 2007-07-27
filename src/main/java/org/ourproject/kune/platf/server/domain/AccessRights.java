package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;

public class AccessRights {
    private ArrayList<Group> admin;
    private ArrayList<Group> edit;
    private ArrayList<Group> view;

    public AccessRights() {
	this.admin = new ArrayList<Group>();
	this.edit = new ArrayList<Group>();
	this.view = new ArrayList<Group>();
    }

    public ArrayList<Group> getAdmin() {
        return admin;
    }

    public void setAdmin(ArrayList<Group> admin) {
        this.admin = admin;
    }

    public ArrayList<Group> getEdit() {
        return edit;
    }

    public void setEdit(ArrayList<Group> edit) {
        this.edit = edit;
    }

    public ArrayList<Group> getView() {
        return view;
    }

    public void setView(ArrayList<Group> view) {
        this.view = view;
    }
    
    
}
