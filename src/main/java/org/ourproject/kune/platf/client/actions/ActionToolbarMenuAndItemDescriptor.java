package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.calclab.suco.client.listener.Listener;

public class ActionToolbarMenuAndItemDescriptor<T> extends ActionToolbarMenuDescriptor<T> {

    public ActionToolbarMenuAndItemDescriptor(final AccessRolDTO accessRolDTO, final ActionToolbarPosition actionToolbarPosition,
	    final Listener<T> onPerformCall) {
	super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarMenuAndItemDescriptor(final AccessRolDTO accessRolDTO, final ActionToolbarPosition actionToolbarPosition,
	    final Listener<T> onPerformCall, final ActionEnableCondition<T> enableCondition) {
	super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

}
