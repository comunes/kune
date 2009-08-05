package org.ourproject.kune.workspace.client.socialnet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;

public class RolComparatorTest {

    @Test
    public void testIsEnabled() {
        final AccessRightsDTO adminRights = new AccessRightsDTO(true, true, true);
        final AccessRightsDTO editorRights = new AccessRightsDTO(false, true, true);
        final AccessRightsDTO viewerRights = new AccessRightsDTO(false, false, true);
        final AccessRightsDTO noPermRights = new AccessRightsDTO(false, false, false);
        assertTrue(RolComparator.isEnabled(AccessRolDTO.Administrator, adminRights));
        assertFalse(RolComparator.isEnabled(AccessRolDTO.Administrator, editorRights));
        assertFalse(RolComparator.isEnabled(AccessRolDTO.Administrator, viewerRights));
        assertFalse(RolComparator.isEnabled(AccessRolDTO.Administrator, noPermRights));
        assertTrue(RolComparator.isEnabled(AccessRolDTO.Editor, adminRights));
        assertTrue(RolComparator.isEnabled(AccessRolDTO.Editor, editorRights));
        assertFalse(RolComparator.isEnabled(AccessRolDTO.Editor, viewerRights));
        assertFalse(RolComparator.isEnabled(AccessRolDTO.Editor, noPermRights));
        assertTrue(RolComparator.isEnabled(AccessRolDTO.Viewer, adminRights));
        assertTrue(RolComparator.isEnabled(AccessRolDTO.Viewer, editorRights));
        assertTrue(RolComparator.isEnabled(AccessRolDTO.Viewer, viewerRights));
        assertFalse(RolComparator.isEnabled(AccessRolDTO.Viewer, noPermRights));
    }

    @Test
    public void testIsMember() {
        assertFalse(RolComparator.isMember(new AccessRightsDTO(false, false, false)));
        assertFalse(RolComparator.isMember(new AccessRightsDTO(false, false, true)));
        assertTrue(RolComparator.isMember(new AccessRightsDTO(false, true, true)));
        assertTrue(RolComparator.isMember(new AccessRightsDTO(true, true, true)));
    }

}
