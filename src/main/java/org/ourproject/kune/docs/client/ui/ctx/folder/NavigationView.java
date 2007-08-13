package org.ourproject.kune.docs.client.ui.ctx.folder;

import org.ourproject.kune.platf.client.View;

public interface NavigationView extends View {
    void clear();
    void add(String name, String type, String event);
    void selectItem(int index);
}
