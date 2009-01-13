package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.workspace.client.socialnet.RadioMustBeChecked;

import com.calclab.suco.client.events.Listener;

public class ActionToolbarMenuRadioDescriptor<T> extends ActionToolbarMenuDescriptor<T> {
    private final String group;
    private final RadioMustBeChecked mustBeChecked;

    public ActionToolbarMenuRadioDescriptor(AccessRolDTO accessRol, ActionToolbarPosition toolbarPosition,
            Listener<T> onPerformCall, String group, RadioMustBeChecked mustBeChecked) {
        super(accessRol, toolbarPosition, onPerformCall);
        this.group = group;
        this.mustBeChecked = mustBeChecked;
    }

    public String getGroup() {
        return group;
    }

    public boolean mustBeChecked() {
        return mustBeChecked.mustBeChecked();
    }
}
