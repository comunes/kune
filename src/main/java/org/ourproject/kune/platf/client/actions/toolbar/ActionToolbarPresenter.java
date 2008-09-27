package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.allen_sauer.gwt.log.client.Log;

public class ActionToolbarPresenter implements ActionToolbar {
    private final ActionToolbarView toolbar;
    private final ActionRegistry<StateToken> actionRegistry;

    public ActionToolbarPresenter(final ActionToolbarView toolbar, final ActionRegistry<StateToken> actionRegistry) {
	this.toolbar = toolbar;
	this.actionRegistry = actionRegistry;
    }

    public void attach() {
	toolbar.attach();
    }

    public void clear() {
	toolbar.clear();
    }

    public void detach() {
	toolbar.detach();
    }

    public void disableMenusAndClearButtons() {
	toolbar.clear();
	// With action-item, this must be redesigned
	// toolbar.clearRemovableActions();
	// toolbar.disableAllMenuItems();
    }

    public void showActions(final ActionItemCollection<StateToken> actions, final boolean isItemSelected) {
	for (final ActionItem<StateToken> actionItem : actions) {
	    final ActionDescriptor<StateToken> action = actionItem.getAction();
	    if (action instanceof ActionMenuDescriptor) {
		toolbar.addMenuAction(actionItem, isItemSelected
			&& actionRegistry.checkEnabling(action, actionItem.getItem()));
	    } else {
		if (action instanceof ActionButtonDescriptor) {
		    if (isItemSelected && actionRegistry.checkEnabling(action, actionItem.getItem())) {
			toolbar.addButtonAction(actionItem);
		    }
		} else {
		    // Code smell
		    Log.error("Not an ActionMenuDescriptor or ActionButtonDescriptor: " + action.getText());
		}
	    }
	}
    }
}
