package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.View;

public interface ContextItemsView extends View {
    void clear();

    void addItem(String name, String type, String event);

    void selectItem(int index);

    void setControlsVisible(boolean isVisible);

    void setCurrentName(String name);

    void setParentButtonEnabled(boolean isEnabled);

    void setParentTreeVisible(boolean b);

}
