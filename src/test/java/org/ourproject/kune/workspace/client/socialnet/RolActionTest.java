package org.ourproject.kune.workspace.client.socialnet;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.shared.dto.AccessRightsDTO;
import cc.kune.core.shared.dto.AccessRolDTO;

public class RolActionTest {

    private AccessRightsDTO adminRights;
    private AccessRightsDTO editorRights;
    private AccessRightsDTO viewerRights;
    private AccessRightsDTO noPermRights;
    private UIStatus ff;
    private UIStatus tt;

    @Before
    public void before() {
        adminRights = new AccessRightsDTO(true, true, true);
        editorRights = new AccessRightsDTO(false, true, true);
        viewerRights = new AccessRightsDTO(false, false, true);
        noPermRights = new AccessRightsDTO(false, false, false);
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
