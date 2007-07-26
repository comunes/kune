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
}
