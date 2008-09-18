package org.ourproject.kune.platf.client.actions;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

public class ActionRegistry {

    private final HashMap<String, ActionCollection<StateToken>> actions;

    public ActionRegistry() {
	actions = new HashMap<String, ActionCollection<StateToken>>();
    }

    public void addAction(final String contentTypeId, final ActionDescriptor<StateToken> action) {
	final ActionCollection<StateToken> actionColl = getActions(contentTypeId);
	actionColl.add(action);
    }

    public boolean checkEnabling(final ActionDescriptor<StateToken> action, final StateToken stateToken) {
	final ActionEnableCondition<StateToken> enableCondition = action.getEnableCondition();
	return enableCondition != null ? enableCondition.mustBeEnabled(stateToken) : true;
    }

    public void removeAction(final String contentTypeId, final ActionDescriptor<StateToken> action) {
	actions.get(contentTypeId).remove(action);
    }

    public ActionCollectionSet<StateToken> selectCurrentActions(final AccessRightsDTO rights, final String contentTypeId) {
	final ActionCollectionSet<StateToken> set = new ActionCollectionSet<StateToken>();
	boolean add = false;

	for (final ActionDescriptor<StateToken> action : getActions(contentTypeId)) {
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

    private ActionCollection<StateToken> getActions(final String contentTypeId) {
	ActionCollection<StateToken> actionColl = actions.get(contentTypeId);
	if (actionColl == null) {
	    actionColl = new ActionCollection<StateToken>();
	    actions.put(contentTypeId, actionColl);
	}
	return actionColl;
    }
}
