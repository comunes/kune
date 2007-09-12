package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SocialNetworkDTO implements IsSerializable {

    AccessListsDTO accessLists;

    GroupListDTO pendingCollaborators;

    public SocialNetworkDTO() {
	accessLists = new AccessListsDTO();
	pendingCollaborators = new GroupListDTO();
    }

    public AccessListsDTO getAccessLists() {
	return accessLists;
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
	this.accessLists = accessLists;
    }

    public GroupListDTO getPendingCollaborators() {
	return pendingCollaborators;
    }

    public void setPendingCollaborators(final GroupListDTO pendingCollaborators) {
	this.pendingCollaborators = pendingCollaborators;
    }

}
