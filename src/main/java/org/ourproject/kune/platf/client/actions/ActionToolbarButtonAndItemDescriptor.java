package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.calclab.suco.client.listener.Listener;

public class ActionToolbarButtonAndItemDescriptor<T> extends ActionToolbarButtonDescriptor<T> {

    public ActionToolbarButtonAndItemDescriptor(final AccessRolDTO accessRolDTO, final ActionToolbarPosition actionToolbarPosition,
	    final Listener<T> onPerformCall) {
	super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarButtonAndItemDescriptor(final AccessRolDTO accessRolDTO, final ActionToolbarPosition actionToolbarPosition,
	    final Listener<T> onPerformCall, final ActionEnableCondition<T> enableCondition) {
	super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

}
