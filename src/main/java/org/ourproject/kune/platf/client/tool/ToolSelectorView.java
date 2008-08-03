package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

public interface ToolSelectorView extends View {

    void setTheme(WsTheme oldTheme, WsTheme newTheme);
}
