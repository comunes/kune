package org.ourproject.kune.docs.client.ui.ctx.folder;

import org.ourproject.kune.platf.client.View;

public interface FolderContentView extends View {
    void clear();

    void add(String name, String type, String event);

    void selectItem(int index);

    void setAddControlsVisibles(boolean b, boolean c);

    void setCurrentName(String name);

    void setParentButtonVisible(boolean b);

}
