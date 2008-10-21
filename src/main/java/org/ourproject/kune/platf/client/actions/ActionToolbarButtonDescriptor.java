package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.calclab.suco.client.listener.Listener;

public class ActionToolbarButtonDescriptor<T> extends ActionToolbarDescriptor<T> {

    private ActionToolbarButtonSeparator leftSeparator;
    private ActionToolbarButtonSeparator rightSeparator;

    public ActionToolbarButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarButtonDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

    public ActionToolbarButtonSeparator getLeftSeparator() {
        return leftSeparator;
    }

    public ActionToolbarButtonSeparator getRightSeparator() {
        return rightSeparator;
    }

    public boolean hasLeftSeparator() {
        return leftSeparator != null;
    }

    public boolean hasRightSeparator() {
        return rightSeparator != null;
    }

    public void setLeftSeparator(final ActionToolbarButtonSeparator leftSeparator) {
        this.leftSeparator = leftSeparator;
    }

    public void setRightSeparator(final ActionToolbarButtonSeparator rightSeparator) {
        this.rightSeparator = rightSeparator;
    }
}
