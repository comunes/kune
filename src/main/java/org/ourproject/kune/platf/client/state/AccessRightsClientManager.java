package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;

import com.calclab.suco.client.events.Event2;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener2;

public class AccessRightsClientManager {
    private AccessRightsDTO previousRights;
    private final Event2<AccessRightsDTO, AccessRightsDTO> onRightsChanged;

    public AccessRightsClientManager(final StateManager stateManager) {
        this.previousRights = null;
        this.onRightsChanged = new Event2<AccessRightsDTO, AccessRightsDTO>("onRightsChanged");
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO newState) {
                final AccessRightsDTO rights = newState.getGroupRights();
                if (!rights.equals(previousRights)) {
                    onRightsChanged.fire(previousRights, rights);
                    previousRights = rights;
                }
            }
        });
    }

    public void onRightsChanged(final Listener2<AccessRightsDTO, AccessRightsDTO> listener) {
        onRightsChanged.add(listener);
    }
}
