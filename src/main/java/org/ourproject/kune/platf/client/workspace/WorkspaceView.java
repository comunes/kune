package org.ourproject.kune.platf.client.workspace;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public interface WorkspaceView {
    void addTab(String name, String caption);
    void setLogo(String groupName);
    void setLogo(Image image);
    void setContextTitle(String title);
    void setContent(Widget content);
    void setContextMenu(Widget contextMenu);
    void setTool(String toolName);
}
