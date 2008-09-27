package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.dto.StateToken;

public interface ActionToolbarView {

    void addButtonAction(ActionItem<StateToken> action);

    void addMenuAction(ActionItem<StateToken> action, boolean enable);

    void attach();

    void clear();

    void clearRemovableActions();

    void detach();

    void disableAllMenuItems();

}
