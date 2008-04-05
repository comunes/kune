
package org.ourproject.kune.workspace.client.theme;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.ThemeMenuComponent;

public class ThemeMenuPresenter implements ThemeMenuComponent {

    private ThemeMenuView view;

    public void chooseTheme(final String theme) {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.CHANGE_GROUP_WSTHEME, theme);
    }

    public void init(final ThemeMenuView view) {
        this.view = view;
    }

    public void setThemes(final String[] themes) {
        view.setThemes(themes);
    }

    public View getView() {
        return view;
    }

    public void setVisible(final boolean visible) {
        view.setVisible(visible);
    }
}
