package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.calclab.suco.client.listener.Listener;

public abstract class ActionToolbarDescriptor<T> extends ActionDescriptor<T> {

    private ActionToolbarPosition actionToolbarPosition;

    public ActionToolbarDescriptor(final AccessRolDTO accessRolDTO, final ActionToolbarPosition actionToolbarPosition,
            final Listener<T> onPerformCall) {
        super(accessRolDTO, onPerformCall);
        this.actionToolbarPosition = actionToolbarPosition;
    }

    public ActionToolbarDescriptor(final AccessRolDTO accessRolDTO, final ActionToolbarPosition actionToolbarPosition,
            final Listener<T> onPerformCall, final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, onPerformCall, enableCondition);
        this.actionToolbarPosition = actionToolbarPosition;
    }

    public ActionToolbarPosition getActionPosition() {
        return actionToolbarPosition;
    }

    public void setActionPosition(final ActionToolbarPosition actionToolbarPosition) {
        this.actionToolbarPosition = actionToolbarPosition;
    }

}
