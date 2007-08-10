package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Image;

public interface WorkspaceView extends View {
    void addTab(String name, String caption);

    void setLogo(String groupName);

    void setLogo(Image image);

    void setContextTitle(String title);

    void setContent(View contentView);

    void setContext(View contextView);

    void setTool(String toolName);

    void adjustSize(int windowWidth, int clientHeight);
}
