/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
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
    private AccessLists containerAccessLists;
    private AccessLists groupAccessLists;
    private AccessRights contentRights;
    private AccessRights containerRights;
    private AccessRights groupRights;

    public Access(final Content descriptor, final Container container) {
        setContent(descriptor);
        setContainer(container);
    }

    public Container getContainer() {
        return container;
    }

    public AccessLists getContainerAccessLists() {
        return containerAccessLists;
    }

    public AccessRights getContainerRights() {
        return containerRights;
    }

    public Content getContent() {
        return content;
    }

    public AccessLists getContentAccessLists() {
        return contentAccessLists;
    }

    public AccessRights getContentRights() {
        return contentRights;
    }

    public AccessLists getGroupAccessLists() {
        return groupAccessLists;
    }

    public AccessRights getGroupRights() {
        return groupRights;
    }

    public boolean hasContainerRights() {
        return containerRights != null;
    }

    public boolean hasContentRights() {
        return contentRights != null;
    }

    public boolean hasGroupRights() {
        return groupRights != null;
    }

    public void setContainer(final Container container) {
        this.container = container;
        containerAccessLists = container != null ? getContainerAccessLists(container) : null;
        groupAccessLists = containerAccessLists;
    }

    public void setContainerRights(final AccessRights accessRights) {
        this.containerRights = accessRights;
        if (equalsAccessLists()) {
            this.contentRights = accessRights;
        }
    }

    public void setContent(final Content descriptor) {
        this.content = descriptor;
        contentAccessLists = descriptor != null ? getContentAccessList(descriptor) : null;
    }

    public void setContentRights(final AccessRights accessRights) {
        this.contentRights = accessRights;
        if (equalsAccessLists()) {
            this.containerRights = accessRights;
        }
    }

    public void setContentWidthFolderRights(final Content content) {
        setContent(content);
        this.contentRights = containerRights;
    }

    public void setGroupRights(final AccessRights accessRights) {
        this.groupRights = accessRights;
    }

    private boolean equalsAccessLists() {
        if (contentAccessLists == containerAccessLists) {
            return true;
        } else if (contentAccessLists != null) {
            return contentAccessLists.equals(containerAccessLists);
        } else {
            return containerAccessLists.equals(contentAccessLists);
        }
    }

    private AccessLists getContentAccessList(final Content descriptor) {
        AccessLists accessLists;
        if (descriptor.hasAccessList()) {
            accessLists = descriptor.getAccessLists();
        } else {
            final SocialNetwork socialNetwork = descriptor.getContainer().getOwner().getSocialNetwork();
            accessLists = socialNetwork.getAccessLists();
        }
        return accessLists;
    }

    private AccessLists getContainerAccessLists(final Container container) {
        return container.getOwner().getSocialNetwork().getAccessLists();
    }

}
