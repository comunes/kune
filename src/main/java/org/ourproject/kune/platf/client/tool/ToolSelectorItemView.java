package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

public interface ToolSelectorItemView extends View {

    void setLink(String text, String targetHistoryToken);

    void setSelected(boolean selected);

    void setTheme(WsTheme oldTheme, WsTheme newTheme);
}
