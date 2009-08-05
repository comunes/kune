package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;

public final class RolComparator {

    public static boolean isEnabled(final AccessRolDTO rolRequired, final AccessRightsDTO rights) {
        switch (rolRequired) {
        case Administrator:
            return rights.isAdministrable();
        case Editor:
            return rights.isEditable();
        case Viewer:
            return rights.isVisible();
        }
        return false;
    }

    public static boolean isMember(final AccessRightsDTO newRights) {
        return newRights.isAdministrable() || newRights.isEditable();
    }

    private RolComparator() {
    }
}
