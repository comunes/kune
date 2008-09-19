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

    public void removeAction(final String contentTypeId, final ActionDescriptor<T> action) {
	actions.get(contentTypeId).remove(action);
    }

    public ActionCollectionSet<T> selectCurrentActions(final AccessRightsDTO rights, final String contentTypeId) {
	final ActionCollectionSet<T> set = new ActionCollectionSet<T>();
	boolean add = false;

	for (final ActionDescriptor<T> action : getActions(contentTypeId)) {
	    switch (action.getAccessRol()) {
	    case Administrator:
		add = rights.isAdministrable();
		break;
	    case Editor:
		add = rights.isEditable();
		break;
	    case Viewer:
		add = rights.isVisible();
		break;
	    }
	    if (add) {
		switch (action.getActionPosition()) {
		case topbarAndItemMenu:
		    set.getItemActions().add(action);
		case topbar:
		    set.getToolbarActions().add(action);
		    break;
		case bootombarAndItemMenu:
		    set.getItemActions().add(action);
		case bottombar:
		    set.getToolbarActions().add(action);
		    break;
		case itemMenu:
		    set.getItemActions().add(action);
		    break;
		}
	    }
	}
	return set;
    }

    private ActionCollection<T> getActions(final String contentTypeId) {
	ActionCollection<T> actionColl = actions.get(contentTypeId);
	if (actionColl == null) {
	    actionColl = new ActionCollection<T>();
	    actions.put(contentTypeId, actionColl);
	}
	return actionColl;
    }
}
