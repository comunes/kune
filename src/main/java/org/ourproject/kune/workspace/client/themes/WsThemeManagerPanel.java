package org.ourproject.kune.workspace.client.themes;

import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.events.Listener2;

public class WsThemeManagerPanel {
    public WsThemeManagerPanel(final WsThemeManager presenter, final WorkspaceSkeleton wskel) {
        presenter.addOnThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                wskel.setTheme(oldTheme, newTheme);
            }
        });
    }
}
