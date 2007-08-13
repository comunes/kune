package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.tool.ToolTrigger;

import com.google.gwt.user.client.ui.Image;

public interface WorkspaceView extends View {
    void setLogo(String groupName);

    void setLogo(Image image);

    void setContextTitle(String title);

    void setContent(View contentView);

    void setContext(View contextView);

    void setTool(String toolName);

    void adjustSize(int windowWidth, int clientHeight);

    void addTab(ToolTrigger trigger);
}
