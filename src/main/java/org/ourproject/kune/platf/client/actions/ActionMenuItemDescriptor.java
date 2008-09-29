package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.calclab.suco.client.listener.Listener;

public class ActionMenuItemDescriptor<T> extends ActionDescriptor<T> {

    public ActionMenuItemDescriptor(final AccessRolDTO accessRolDTO, final Listener<T> onPerformCall) {
	super(accessRolDTO, onPerformCall);
    }

    public ActionMenuItemDescriptor(final AccessRolDTO accessRolDTO, final Listener<T> onPerformCall,
	    final ActionEnableCondition<T> enableCondition) {
	super(accessRolDTO, onPerformCall, enableCondition);
    }

}
