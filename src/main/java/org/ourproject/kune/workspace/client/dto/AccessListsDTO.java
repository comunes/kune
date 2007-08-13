package org.ourproject.kune.workspace.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AccessListsDTO implements IsSerializable {

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.GroupDTO>
     */
    private List admin;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.GroupDTO>
     */
    private List edit;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.GroupDTO>
     */
    private List view;

    public AccessListsDTO() {
	this(null, null, null);
    }

    public AccessListsDTO(final List admin, final List edit, final List view) {
	this.admin = admin;
	this.edit = edit;
	this.view = view;
    }

    public List getAdmin() {
	return admin;
    }

    public void setAdmin(final List admin) {
	this.admin = admin;
    }

    public List getEdit() {
	return edit;
    }

    public void setEdit(final List edit) {
	this.edit = edit;
    }

    public List getView() {
	return view;
    }

    public void setView(final List view) {
	this.view = view;
    }

}
