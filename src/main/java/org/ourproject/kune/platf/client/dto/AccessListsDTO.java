
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AccessListsDTO implements IsSerializable {
    private GroupListDTO admins;
    private GroupListDTO editors;
    private GroupListDTO viewers;

    public AccessListsDTO() {
        this(null, null, null);
    }

    public AccessListsDTO(final GroupListDTO admins, final GroupListDTO editors, final GroupListDTO viewers) {
        this.admins = admins;
        this.editors = editors;
        this.viewers = viewers;
    }

    public GroupListDTO getAdmins() {
        return admins;
    }

    public void setAdmins(final GroupListDTO admins) {
        this.admins = admins;
    }

    public GroupListDTO getEditors() {
        return editors;
    }

    public void setEditors(final GroupListDTO editors) {
        this.editors = editors;
    }

    public GroupListDTO getViewers() {
        return viewers;
    }

    public void setViewers(final GroupListDTO viewers) {
        this.viewers = viewers;
    }

}
