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

import javax.persistence.NoResultException;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.NoDefaultContentException;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AccessServiceDefault implements AccessService {

    private final FinderService finder;
    private final AccessRightsService accessRightsService;
    private final Group groupFinder;

    @Inject
    public AccessServiceDefault(final FinderService finder, final AccessRightsService accessRightsService,
            final Group groupFinder) {
        this.finder = finder;
        this.accessRightsService = accessRightsService;
        this.groupFinder = groupFinder;
    }

    public Container accessToContainer(final Long folderId, final User user, final AccessRol accessRol)
            throws DefaultException {
        final Container folder = finder.getFolder(folderId);
        final Access access = new Access(null, folder);
        addFolderRights(access, user);
        addGroupRights(access, user);
        return check(access, access.getContainerRights(), accessRol).getContainer();
    }

    public Content accessToContent(final Long contentId, final User user, final AccessRol accessRol)
            throws DefaultException {
        final Content descriptor = finder.getContent(contentId);
        final Access access = new Access(descriptor, null);
        addContentRights(access, user);
        return check(access, access.getContentRights(), accessRol).getContent();
    }

    public Access getAccess(final User user, final StateToken token, final Group defaultGroup, final AccessRol accessRol)
            throws DefaultException {
        checkGroupExistence(token);
        final Content descriptor = finder.getContent(token, defaultGroup);
        if (descriptor.equals(Content.NO_CONTENT)) {
            throw new NoDefaultContentException();
        }
        final Access access = new Access(descriptor, descriptor.getContainer());
        addContentRights(access, user);
        addFolderRights(access, user);
        addGroupRights(access, user);
        if (!isValid(accessRol, access.getContentRights()) || !isValid(accessRol, access.getContainerRights())) {
            throw new AccessViolationException();
        }
        return access;
    }

    private void addContentRights(final Access access, final User user) {
        if (!access.hasContentRights()) {
            access.setContentRights(accessRightsService.get(user, access.getContentAccessLists()));
        }
    }

    private void addFolderRights(final Access access, final User user) {
        if (!access.hasContainerRights()) {
            access.setContainerRights(accessRightsService.get(user, access.getContainerAccessLists()));
        }
    }

    private void addGroupRights(final Access access, final User user) {
        if (!access.hasGroupRights()) {
            access.setGroupRights(accessRightsService.get(user, access.getGroupAccessLists()));
        }
    }

    private Access check(final Access access, final AccessRights rights, final AccessRol accessRol)
            throws AccessViolationException {
        if (!isValid(accessRol, rights)) {
            throw new AccessViolationException();
        }
        return access;
    }

    private void checkGroupExistence(final StateToken token) throws ContentNotFoundException {
        if (token.hasGroup()) {
            try {
                final String tokenGroup = token.getGroup();
                groupFinder.findByShortName(tokenGroup);
            } catch (final NoResultException e) {
                throw new ContentNotFoundException();
            }
        }
    }

    private boolean isValid(final AccessRol accessRol, final AccessRights rights) {
        switch (accessRol) {
        case Viewer:
            return rights.isVisible();
        case Editor:
            return rights.isEditable();
        case Administrator:
            return rights.isAdministrable();
        default:
            return false;
        }
    }

}
