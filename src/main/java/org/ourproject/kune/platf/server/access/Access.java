/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.access;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.SocialNetwork;

public class Access {
    private Content content;
    private Container container;
    private AccessLists contentAccessLists;
    private AccessLists folderAccessLists;
    private AccessLists groupAccessLists;
    private AccessRights contentRights;
    private AccessRights folderRights;
    private AccessRights groupRights;

    public Access(final Content descriptor, final Container container) {
	setContent(descriptor);
	setFolder(container);
    }

    public Container getFolder() {
	return container;
    }

    public void setContent(final Content descriptor) {
	this.content = descriptor;
	contentAccessLists = descriptor != null ? getContentAccessList(descriptor) : null;
    }

    public void setFolder(final Container container) {
	this.container = container;
	folderAccessLists = container != null ? getFolderAccessLists(container) : null;
	groupAccessLists = folderAccessLists;
    }

    public Content getContent() {
	return content;
    }

    public AccessLists getContentAccessLists() {
	return contentAccessLists;
    }

    public AccessLists getFolderAccessLists() {
	return folderAccessLists;
    }

    public AccessLists getGroupAccessLists() {
	return groupAccessLists;
    }

    public AccessRights getContentRights() {
	return contentRights;
    }

    public AccessRights getFolderRights() {
	return folderRights;
    }

    public AccessRights getGroupRights() {
	return groupRights;
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

    public void setGroupRights(final AccessRights accessRights) {
	this.groupRights = accessRights;
    }

    public boolean hasContentRights() {
	return contentRights != null;
    }

    public boolean hasFolderRights() {
	return folderRights != null;
    }

    public boolean hasGroupRights() {
	return groupRights != null;
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
	AccessLists accessLists;
	if (descriptor.hasAccessList()) {
	    accessLists = descriptor.getAccessLists();
	} else {
	    SocialNetwork socialNetwork = descriptor.getContainer().getOwner().getSocialNetwork();
	    accessLists = socialNetwork.getAccessLists();
	}
	return accessLists;
    }

    private AccessLists getFolderAccessLists(final Container container) {
	return container.getOwner().getSocialNetwork().getAccessLists();
    }

    public void setContentWidthFolderRights(final Content content) {
	this.content = content;
	this.contentRights = folderRights;
    }

}
