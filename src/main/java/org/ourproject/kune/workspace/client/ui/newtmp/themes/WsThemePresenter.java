package org.ourproject.kune.workspace.client.ui.newtmp.themes;

public class WsThemePresenter {

    private WsThemeView view;

    public void init(final WsThemeView view) {
	this.view = view;
    }

    public void setVisible(final boolean visible) {
	view.setVisible(visible);
    }
}
