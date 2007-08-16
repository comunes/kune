package org.ourproject.kune.platf.server.access;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.SocialNetwork;

public class Access {
    private Content descriptor;
    private AccessLists contentAccessLists;
    private AccessLists folderAccessLists;
    private AccessRights contentRights;
    private AccessRights folderRights;
    private Folder folder;

    public Access(final Content descriptor, final Folder folder) {
	this.descriptor = descriptor;
	contentAccessLists = descriptor != null ? getContentAccessList(descriptor) : null;
	this.folder = folder;
	folderAccessLists = folder != null ? getFolderAccessLists(folder) : null;
    }

    public Folder getFolder() {
	return folder;
    }

    public void setFolder(final Folder folder) {
	this.folder = folder;
	folderAccessLists = folder != null ? getFolderAccessLists(folder) : null;
    }

    public void setDescriptor(final Content descriptor) {
	this.descriptor = descriptor;
	contentAccessLists = descriptor != null ? getContentAccessList(descriptor) : null;
    }

    public Content getDescriptor() {
	return descriptor;
    }

    public AccessLists getContentAccessLists() {
	return contentAccessLists;
    }

    public AccessLists getFolderAccessLists() {
	return folderAccessLists;
    }

    public AccessRights getContentRights() {
	return contentRights;
    }

    public AccessRights getFolderRights() {
	return folderRights;
    }

    public void setContentRights(final AccessRights accessRights) {
	this.contentRights = accessRights;
	if (equalsAccessLists()) {
	    this.folderRights = accessRights;
	}
    }

    public void setFolderRights(final AccessRights accessRights) {
	this.folderRights = accessRights;
	if (equalsAccessLists()) {
	    this.contentRights = accessRights;
	}
    }

    public boolean hasFolderRights() {
	return folderRights != null;
    }

    public boolean hasContentRights() {
	return contentRights != null;
    }

    private boolean equalsAccessLists() {
	if (contentAccessLists == folderAccessLists) {
	    return true;
	} else if (contentAccessLists != null) {
	    return contentAccessLists.equals(folderAccessLists);
	} else {
	    return folderAccessLists.equals(contentAccessLists);
	}
    }

    private AccessLists getContentAccessList(final Content descriptor) {
	AccessLists accessLists = descriptor.getAccessLists();
	if (accessLists == null) {
	    SocialNetwork socialNetwork = descriptor.getFolder().getOwner().getSocialNetwork();
	    accessLists = socialNetwork.getAccessList();
	}
	return accessLists;
    }

    private AccessLists getFolderAccessLists(final Folder folder) {
	return folder.getOwner().getSocialNetwork().getAccessList();
    }

    public void setDescriptorWidthFolderRights(final Content content) {
	this.descriptor = content;
	this.contentRights = folderRights;
    }

}
