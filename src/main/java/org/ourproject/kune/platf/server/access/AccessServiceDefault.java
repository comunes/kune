/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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


import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AccessServiceDefault implements AccessService {

    private final FinderService finder;
    private final AccessRightsService accessRightsService;

    @Inject
    public AccessServiceDefault(final FinderService finder, final AccessRightsService accessRightsService) {
        this.finder = finder;
        this.accessRightsService = accessRightsService;
    }

    public Container accessToContainer(final Long folderId, final User user, final AccessRol accessRol)
            throws DefaultException {
        final Container container = finder.getFolder(folderId);
        check(accessRightsService.get(user, container.getAccessLists()), accessRol);
        return container;
    }

    public Content accessToContent(final Long contentId, final User user, final AccessRol accessRol)
            throws DefaultException {
        final Content content = finder.getContent(contentId);
        AccessLists accessLists = content.getAccessLists();
        check(accessRightsService.get(user, accessLists), accessRol);
        return content;
    }

    private void check(final AccessRights rights, final AccessRol accessRol) throws AccessViolationException {
        if (!isValid(accessRol, rights)) {
            throw new AccessViolationException();
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
