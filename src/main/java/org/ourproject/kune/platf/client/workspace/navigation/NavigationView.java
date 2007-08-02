package org.ourproject.kune.platf.client.workspace.navigation;

import org.ourproject.kune.platf.client.View;

public interface NavigationView extends View {
    void clear();
    void add(String name, String type, String event);
    void selectItem(int index);
}
