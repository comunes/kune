package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.listener.Listener2;

public class ToolSelectorItemPresenter implements ToolSelectorItem {

    private ToolSelectorItemView view;
    private final ToolSelector toolSelector;
    private final WsThemePresenter wsThemePresenter;
    private final String shortName;
    private final String longName;

    public ToolSelectorItemPresenter(final String shortName, final String longName, final ToolSelector toolSelector,
            final WsThemePresenter wsThemePresenter) {
        this.shortName = shortName;
        this.longName = longName;
        this.toolSelector = toolSelector;
        this.wsThemePresenter = wsThemePresenter;
    }

    public String getShortName() {
        return shortName;
    }

    public View getView() {
        return view;
    }

    public void init(final ToolSelectorItemView view) {
        this.view = view;
        toolSelector.addTool(this);
        wsThemePresenter.onThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                setTheme(oldTheme, newTheme);
            }
        });
    }

    public void setGroupShortName(final String groupShortName) {
        final StateToken token = new StateToken(groupShortName, getShortName(), null, null);
        view.setLink(longName, token.toString());
    }

    public void setSelected(final boolean selected) {
        view.setSelected(selected);
    }

    public void setVisible(boolean visible) {
        view.setVisible(visible);
    }

    private void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
        view.setTheme(oldTheme, newTheme);
    }
}
