package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.dto.StateToken;

public interface ActionToolbar {

    void clear();

    void disableMenusAndClearButtons();

    void showActions(ActionItemCollection<StateToken> actionItemCollection, boolean isItemSelected);

}
