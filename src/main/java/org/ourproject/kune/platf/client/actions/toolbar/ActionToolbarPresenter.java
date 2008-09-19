package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;

import com.allen_sauer.gwt.log.client.Log;

public class ActionToolbarPresenter implements ActionToolbar {
    private final ActionToolbarPanel toolbar;
    private final Session session;
    private final ActionRegistry<StateToken> actionRegistry;

    public ActionToolbarPresenter(final Session session, final ActionToolbarPanel actionToolbarPanel,
	    final ActionRegistry<StateToken> actionRegistry) {
	this.session = session;
	toolbar = actionToolbarPanel;
	this.actionRegistry = actionRegistry;
    }

    public void clear() {
	toolbar.clear();
    }

    public void disableMenusAndClearButtons() {
	toolbar.clearRemovableActions();
	toolbar.disableAllMenuItems();
    }

    public void setActions(final ActionCollection<StateToken> actions, final boolean isItemSelected) {
	for (final ActionDescriptor<StateToken> action : actions) {
	    if (action instanceof ActionMenuDescriptor) {
		toolbar.addMenuAction((ActionMenuDescriptor<StateToken>) action, isItemSelected
			&& actionRegistry.checkEnabling(action, session.getCurrentStateToken()));
	    } else {
		if (action instanceof ActionButtonDescriptor) {
		    if (isItemSelected && actionRegistry.checkEnabling(action, session.getCurrentStateToken())) {
			toolbar.addButtonAction((ActionButtonDescriptor<StateToken>) action);
		    }
		} else {
		    // Code smell
		    Log.error("Not an ActionMenuDescriptor or ActionButtonDescriptor");
		}
	    }
	}
    }

}
