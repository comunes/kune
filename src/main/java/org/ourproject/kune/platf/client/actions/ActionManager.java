package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.listener.Listener0;

public class ActionManager {

    private final WorkspaceSkeleton ws;

    public ActionManager(final WorkspaceSkeleton ws) {
	this.ws = ws;
    }

    public void doAction(final ActionItem<?> actionItem) {
	final ActionDescriptor<?> action = actionItem.getAction();
	final Object item = actionItem.getItem();
	if (action.isMustBeConfirmed()) {
	    ws.askConfirmation(action.getConfirmationTitle(), action.getConfirmationText(), new Listener0() {
		public void onEvent() {
		    fire(action, item);
		}
	    }, new Listener0() {
		public void onEvent() {
		    action.fireOnNotConfirmed(item);
		}
	    });
	} else {
	    fire(action, item);
	}
    }

    private void fire(final ActionDescriptor<?> action, final Object parameter) {
	action.fireOnPerformCall(parameter);
    }

}
