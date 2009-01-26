package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.calclab.suco.client.events.Listener;

public class ActionToolbarMenuCheckItemDescriptor<T> extends ActionToolbarMenuDescriptor<T> {

    private final ActionCheckedCondition mustBeChecked;

    public ActionToolbarMenuCheckItemDescriptor(AccessRolDTO accessRol, ActionToolbarPosition toolbarPosition,
            Listener<T> onPerformCall, ActionCheckedCondition mustBeChecked) {
        super(accessRol, toolbarPosition, onPerformCall);
        this.mustBeChecked = mustBeChecked;
    }

    public ActionCheckedCondition getMustBeChecked() {
        return mustBeChecked;
    }

}
