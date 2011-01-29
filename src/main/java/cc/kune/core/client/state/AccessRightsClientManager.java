package cc.kune.core.client.state;

import cc.kune.core.shared.domain.utils.AccessRights;

import com.calclab.suco.client.events.Listener2;

public class AccessRightsClientManager {
    private final AccessRights previousRights;

    // private final Event2<AccessRights, AccessRights> onRightsChanged;

    public AccessRightsClientManager(final StateManager stateManager) {
        this.previousRights = null;
        // this.onRightsChanged = new Event2<AccessRights , AccessRights
        // >("onRightsChanged");
        // stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
        // public void onEvent(final StateAbstractDTO newState) {
        // final AccessRights rights = newState.getGroupRights();
        // if (!rights.equals(previousRights)) {
        // onRightsChanged.fire(previousRights, rights);
        // previousRights = rights;
        // }
        // }
        // });
    }

    public void onRightsChanged(final Listener2<AccessRights, AccessRights> listener) {
        // onRightsChanged.add(listener);
    }
}
