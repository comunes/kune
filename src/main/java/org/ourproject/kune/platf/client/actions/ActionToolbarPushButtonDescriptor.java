package org.ourproject.kune.platf.client.actions;



import cc.kune.core.shared.dto.AccessRolDTO;

import com.calclab.suco.client.events.Listener0;

public class ActionToolbarPushButtonDescriptor<T> extends ActionToolbarButtonDescriptor<T> {

    private ActionPressedCondition<T> mustInitialyPressed;

    public ActionToolbarPushButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener0 onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionPressedCondition<T> getMustInitialyPressed() {
        return mustInitialyPressed;
    }

    public void setMustInitialyPressed(final ActionPressedCondition<T> mustInitialyPressed) {
        this.mustInitialyPressed = mustInitialyPressed;
    }

}
