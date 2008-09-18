package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionCollection;
import org.ourproject.kune.platf.client.dto.StateToken;

public interface ActionToolbar {

    void clear();

    void disableMenusAndClearButtons();

    void setActions(ActionCollection<StateToken> actionCollection, boolean isItemSelected);

}
