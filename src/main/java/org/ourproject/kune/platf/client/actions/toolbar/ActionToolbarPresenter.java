package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;

import com.allen_sauer.gwt.log.client.Log;

public class ActionToolbarPresenter<T> implements ActionToolbar<T> {
    private final ActionToolbarView<T> toolbar;
    private final ActionRegistry<T> actionRegistry;

    public ActionToolbarPresenter(final ActionToolbarView<T> toolbar, final ActionRegistry<T> actionRegistry) {
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

    public void showActions(final ActionItemCollection<T> actions, final boolean isItemSelected) {
	for (final ActionItem<T> actionItem : actions) {
	    final ActionDescriptor<T> action = actionItem.getAction();
	    if (action instanceof ActionToolbarMenuDescriptor || action instanceof ActionToolbarMenuAndItemDescriptor) {
		toolbar.addMenuAction(actionItem, isItemSelected
			&& actionRegistry.checkEnabling(action, actionItem.getItem()));
	    } else {
		if (action instanceof ActionToolbarButtonDescriptor
			|| action instanceof ActionToolbarButtonAndItemDescriptor) {
		    if (isItemSelected && actionRegistry.checkEnabling(action, actionItem.getItem())) {
			toolbar.addButtonAction(actionItem);
		    }
		} else {
		    Log.error("Code error: Not an ActionMenuDescriptor or ActionButtonDescriptor: " + action.getText());
		}
	    }
	}
    }
}
