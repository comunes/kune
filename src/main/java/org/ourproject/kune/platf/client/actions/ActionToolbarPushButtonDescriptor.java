package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class ActionToolbarPushButtonDescriptor<T> extends ActionToolbarButtonDescriptor<T> {

    ActionPressedCondition<T> mustInitialyPressed;

    public ActionToolbarPushButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarPushButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall,
            final ActionAddCondition<T> addCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, addCondition);
    }

    public ActionToolbarPushButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

    public ActionToolbarPushButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener0 onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarPushButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener0 onPerformCall,
            final ActionAddCondition<T> addCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, addCondition);
    }

    public ActionToolbarPushButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener0 onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

    public ActionPressedCondition<T> getMustInitialyPressed() {
        return mustInitialyPressed;
    }

    public void setMustInitialyPressed(ActionPressedCondition<T> mustInitialyPressed) {
        this.mustInitialyPressed = mustInitialyPressed;
    }

}
