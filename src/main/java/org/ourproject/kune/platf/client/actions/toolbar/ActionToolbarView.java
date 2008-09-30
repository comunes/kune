package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionItem;

public interface ActionToolbarView<T> {

    void addButtonAction(ActionItem<T> action);

    void addMenuAction(ActionItem<T> action, boolean enable);

    void attach();

    void clear();

    void clearRemovableActions();

    void detach();

    void disableAllMenuItems();

}
