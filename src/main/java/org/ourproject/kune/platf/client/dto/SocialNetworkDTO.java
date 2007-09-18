package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SocialNetworkDTO implements IsSerializable {

    public static final String REQ_JOIN_WAITING_MODERATION = "MODERATED";
    public static final String REQ_JOIN_ACEPTED = "ACEPTED";
    public static final String REQ_JOIN_DENIED = "DENIED";

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
