package cc.kune.core.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;

public abstract class RolAction extends AbstractExtendedAction {

    private final boolean authNeed;
    private final AccessRolDTO rolRequired;

    /**
     * @param rolRequired
     *            the Rol required to allow this action
     * @param authNeed
     *            if we need to be authenticated to execute this action
     */
    @Inject
    public RolAction(final AccessRolDTO rolRequired, final boolean authNeed) {
        this.rolRequired = rolRequired;
        this.authNeed = authNeed;
    }

    public AccessRolDTO getRolRequired() {
        return rolRequired;
    }

    public boolean isAuthNeed() {
        return authNeed;
    }

}
