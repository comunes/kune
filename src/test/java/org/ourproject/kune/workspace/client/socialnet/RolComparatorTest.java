package org.ourproject.kune.workspace.client.socialnet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;

public class RolComparatorTest {

    @Test
    public void testIsEnabled() {
        final AccessRights adminRights = new AccessRights(true, true, true);
        final AccessRights editorRights = new AccessRights(false, true, true);
        final AccessRights viewerRights = new AccessRights(false, false, true);
        final AccessRights noPermRights = new AccessRights(false, false, false);
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
        assertFalse(RolComparator.isMember(new AccessRights(false, false, false)));
        assertFalse(RolComparator.isMember(new AccessRights(false, false, true)));
        assertTrue(RolComparator.isMember(new AccessRights(false, true, true)));
        assertTrue(RolComparator.isMember(new AccessRights(true, true, true)));
    }

}
