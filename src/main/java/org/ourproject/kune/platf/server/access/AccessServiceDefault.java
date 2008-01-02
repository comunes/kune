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

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AccessServiceDefault implements AccessService {

    private final FinderService finder;
    private final AccessRightsService accessRightsService;

    @Inject
    public AccessServiceDefault(final FinderService finder) {
        this(finder, new AccessRightsServiceDefault());
    }

    public AccessServiceDefault(final FinderService finder, final AccessRightsService accessRightsService) {
        this.finder = finder;
        this.accessRightsService = accessRightsService;
    }

    public Access getAccess(final User user, final StateToken token, final Group defaultGroup,
            final AccessType accessType) throws SerializableException {
        Content descriptor = finder.getContent(token, defaultGroup);
        Access access = new Access(descriptor, descriptor.getFolder());
        addContentRights(access, user);
        addFolderRights(access, user);
        addGroupRights(access, user);
        if (!isValid(accessType, access.getContentRights()) || !isValid(accessType, access.getFolderRights())) {
            throw new AccessViolationException();
        }
        return access;
    }

    public Content accessToContent(final Long contentId, final User user, final AccessType accessType)
            throws SerializableException {
        Content descriptor = finder.getContent(contentId);
        Access access = new Access(descriptor, null);
        addContentRights(access, user);
        return check(access, access.getContentRights(), accessType).getContent();
    }

    public Access getFolderAccess(final Long folderId, final User user, final AccessType accessType)
            throws SerializableException {
        Access access = new Access(null, finder.getFolder(folderId));
        addFolderRights(access, user);
        return check(access, access.getFolderRights(), accessType);
    }

    private Access check(final Access access, final AccessRights rights, final AccessType accessType)
            throws AccessViolationException {
        if (!isValid(accessType, rights)) {
            throw new AccessViolationException();
        }
        return access;
    }

    private boolean isValid(final AccessType accessType, final AccessRights rights) {
        switch (accessType) {
        case READ:
            return rights.isVisible();
        case EDIT:
            return rights.isEditable();
        case ADMIN:
            return rights.isAdministrable();
        default:
            return false;
        }
    }

    private void addGroupRights(final Access access, final User user) {
        if (!access.hasGroupRights()) {
            access.setGroupRights(accessRightsService.get(user, access.getGroupAccessLists()));
        }
    }

    private void addFolderRights(final Access access, final User user) {
        if (!access.hasFolderRights()) {
            access.setFolderRights(accessRightsService.get(user, access.getFolderAccessLists()));
        }
    }

    private void addContentRights(final Access access, final User user) {
        if (!access.hasContentRights()) {
            access.setContentRights(accessRightsService.get(user, access.getContentAccessLists()));
        }
    }

}
