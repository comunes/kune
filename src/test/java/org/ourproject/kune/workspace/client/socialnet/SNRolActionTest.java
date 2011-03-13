/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.socialnet;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.workspace.client.oldsn.RolAction;
import org.ourproject.kune.workspace.client.oldsn.UIStatus;

import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;

public class SNRolActionTest {

    private AccessRights adminRights;
    private AccessRights editorRights;
    private AccessRights viewerRights;
    private AccessRights noPermRights;
    private UIStatus ff;
    private UIStatus tt;

    @Before
    public void before() {
        adminRights = new AccessRights(true, true, true);
        editorRights = new AccessRights(false, true, true);
        viewerRights = new AccessRights(false, false, true);
        noPermRights = new AccessRights(false, false, false);
        ff = new UIStatus(false, false);
        tt = new UIStatus(true, true);
    }

    @Test
    public void testRefreshStatus() {
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Viewer, false, false, true, true, noPermRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Viewer, true, false, true, true, noPermRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Viewer, true, true, true, true, noPermRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Viewer, true, true, false, true, noPermRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Viewer, true, true, false, false, noPermRights));

        assertEquals(tt, RolAction.refreshStatus(AccessRolDTO.Viewer, false, false, true, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Viewer, true, false, true, true, viewerRights));
        assertEquals(tt, RolAction.refreshStatus(AccessRolDTO.Viewer, true, true, true, true, viewerRights));
        assertEquals(tt, RolAction.refreshStatus(AccessRolDTO.Viewer, true, true, false, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Viewer, true, true, false, false, viewerRights));

        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, false, false, true, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, false, true, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, true, true, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, true, false, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, true, false, false, viewerRights));

        assertEquals(tt, RolAction.refreshStatus(AccessRolDTO.Editor, false, false, true, true, editorRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, false, true, true, editorRights));
        assertEquals(tt, RolAction.refreshStatus(AccessRolDTO.Editor, true, true, true, true, editorRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, true, false, true, editorRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, true, false, false, editorRights));

        assertEquals(tt, RolAction.refreshStatus(AccessRolDTO.Editor, false, false, true, true, adminRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, false, true, true, adminRights));
        assertEquals(tt, RolAction.refreshStatus(AccessRolDTO.Editor, true, true, true, true, adminRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, true, false, true, adminRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Editor, true, true, false, false, adminRights));

        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, false, false, true, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, false, true, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, true, true, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, true, false, true, viewerRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, true, false, false, viewerRights));

        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, false, false, true, true, editorRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, false, true, true, editorRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, true, true, true, editorRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, true, false, true, editorRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, true, false, false, editorRights));

        assertEquals(tt, RolAction.refreshStatus(AccessRolDTO.Administrator, false, false, true, true, adminRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, false, true, true, adminRights));
        assertEquals(tt, RolAction.refreshStatus(AccessRolDTO.Administrator, true, true, true, true, adminRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, true, false, true, adminRights));
        assertEquals(ff, RolAction.refreshStatus(AccessRolDTO.Administrator, true, true, false, false, adminRights));

    }
}
