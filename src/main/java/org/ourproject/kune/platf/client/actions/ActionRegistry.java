package org.ourproject.kune.platf.client.actions;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.AccessRightsDTO;

public class ActionRegistry<T> {

    private final HashMap<String, ActionCollection<T>> actions;

    public ActionRegistry() {
	actions = new HashMap<String, ActionCollection<T>>();
    }

    public void addAction(final String contentTypeId, final ActionDescriptor<T> action) {
	final ActionCollection<T> actionColl = getActions(contentTypeId);
	actionColl.add(action);
    }

    public boolean checkEnabling(final ActionDescriptor<T> action, final T T) {
	final ActionEnableCondition<T> enableCondition = action.getEnableCondition();
	return enableCondition != null ? enableCondition.mustBeEnabled(T) : true;
    }

    public ActionItemCollection<T> getCurrentActions(final T item, final String contentTypeId,
	    final AccessRightsDTO rights, final boolean toolbarItems) {
	final ActionItemCollection<T> collection = new ActionItemCollection<T>();

	for (final ActionDescriptor<T> action : getActions(contentTypeId)) {
	    if (mustAdd(rights, action)) {
		if (toolbarItems) {
		    switch (action.getActionPosition()) {
		    case topbarAndItemMenu:
		    case topbar:
		    case bootombarAndItemMenu:
		    case bottombar:
			collection.add(new ActionItem<T>(action, item));
			break;
		    }
		} else {
		    switch (action.getActionPosition()) {
		    case itemMenu:
		    case topbarAndItemMenu:
		    case bootombarAndItemMenu:
			collection.add(new ActionItem<T>(action, item));
			break;
		    }
		}
	    }
	}
	return collection;
    }

    public void removeAction(final String contentTypeId, final ActionDescriptor<T> action) {
	actions.get(contentTypeId).remove(action);
    }

    private ActionCollection<T> getActions(final String contentTypeId) {
	ActionCollection<T> actionColl = actions.get(contentTypeId);
	if (actionColl == null) {
	    actionColl = new ActionCollection<T>();
	    actions.put(contentTypeId, actionColl);
	}
	return actionColl;
    }

    private boolean mustAdd(final AccessRightsDTO rights, final ActionDescriptor<T> action) {
	switch (action.getAccessRol()) {
	case Administrator:
	    return rights.isAdministrable();
	case Editor:
	    return rights.isEditable();
	case Viewer:
	default:
	    return rights.isVisible();
	}
    }
}
