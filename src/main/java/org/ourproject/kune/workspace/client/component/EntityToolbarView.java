package org.ourproject.kune.workspace.client.component;

import org.ourproject.kune.platf.client.actions.ActionButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionMenuDescriptor;
import org.ourproject.kune.platf.client.dto.StateToken;

public interface EntityToolbarView {

    void addButtonAction(ActionButtonDescriptor<StateToken> action);

    void addMenuAction(ActionMenuDescriptor<StateToken> action, boolean enable);

    void clear();

    void clearRemovableActions();

    void disableAllMenuItems();

}
