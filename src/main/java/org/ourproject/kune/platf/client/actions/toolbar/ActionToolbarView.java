package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionItem;

public interface ActionToolbarView<T> {

    void addButtonAction(ActionItem<T> actionItem, boolean enable);

    void addMenuAction(ActionItem<T> actionItem, boolean enable);

    void attach();

    void clear();

    void detach();

    void disableAllMenuItems();

    void setButtonEnable(ActionDescriptor<T> action, boolean enable);

    void setMenuEnable(ActionDescriptor<T> action, boolean enable);

}
