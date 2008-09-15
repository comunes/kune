package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.calclab.suco.client.signal.Slot;

public class ActionButtonDescriptor<T> extends ActionDescriptor<T> {

    private ActionButtonSeparator leftSeparator;
    private ActionButtonSeparator rightSeparator;

    public ActionButtonDescriptor(final AccessRolDTO accessRolDTO, final ActionPosition actionPosition,
	    final Slot<T> onPerformCall) {
	super(accessRolDTO, actionPosition, onPerformCall);
    }

    public ActionButtonSeparator getLeftSeparator() {
	return leftSeparator;
    }

    public ActionButtonSeparator getRightSeparator() {
	return rightSeparator;
    }

    public boolean hasLeftSeparator() {
	return leftSeparator != null;
    }

    public boolean hasRightSeparator() {
	return rightSeparator != null;
    }

    public void setLeftSeparator(final ActionButtonSeparator leftSeparator) {
	this.leftSeparator = leftSeparator;
    }

    public void setRightSeparator(final ActionButtonSeparator rightSeparator) {
	this.rightSeparator = rightSeparator;
    }
}
