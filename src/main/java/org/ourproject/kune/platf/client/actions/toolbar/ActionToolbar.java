package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionItemCollection;

public interface ActionToolbar<T> {

    void attach();

    void clear();

    void detach();

    void disableMenusAndClearButtons();

    void showActions(ActionItemCollection<T> actionItemCollection, boolean isItemSelected);

}
