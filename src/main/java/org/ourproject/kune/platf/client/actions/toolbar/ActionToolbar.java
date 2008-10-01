package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionToolbarDescriptor;

public interface ActionToolbar<T> {

    void attach();

    void clear();

    void detach();

    void disableMenusAndClearButtons();

    void setActions(ActionItemCollection<T> actionItemCollection);

    void setEnableAction(ActionToolbarDescriptor<T> action, boolean enable);

}
