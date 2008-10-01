package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;

import com.allen_sauer.gwt.log.client.Log;

public class ActionToolbarPresenter<T> implements ActionToolbar<T> {
    private final ActionToolbarView<T> toolbar;

    public ActionToolbarPresenter(final ActionToolbarView<T> toolbar) {
	this.toolbar = toolbar;
    }

    public void attach() {
	toolbar.attach();
    }

    public boolean checkEnabling(final ActionDescriptor<T> action, final T T) {
	final ActionEnableCondition<T> enableCondition = action.getEnableCondition();
	return enableCondition != null ? enableCondition.mustBeEnabled(T) : true;
    }

    public void clear() {
	toolbar.clear();
    }

    public void detach() {
	toolbar.detach();
    }

    public void disableMenusAndClearButtons() {
	toolbar.clear();
    }

    public void showActions(final ActionItemCollection<T> actions, final boolean isItemSelected) {
	for (final ActionItem<T> actionItem : actions) {
	    final ActionDescriptor<T> action = actionItem.getAction();
	    if (action instanceof ActionToolbarMenuDescriptor || action instanceof ActionToolbarMenuAndItemDescriptor) {
		toolbar.addMenuAction(actionItem, isItemSelected && checkEnabling(action, actionItem.getItem()));
	    } else {
		if (action instanceof ActionToolbarButtonDescriptor
			|| action instanceof ActionToolbarButtonAndItemDescriptor) {
		    if (isItemSelected && checkEnabling(action, actionItem.getItem())) {
			toolbar.addButtonAction(actionItem);
		    }
		} else {
		    Log.error("Code error: Not an ActionMenuDescriptor or ActionButtonDescriptor: " + action.getText());
		}
	    }
	}
    }
}
