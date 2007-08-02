package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Image;

public interface WorkspaceView {
    void addTab(String name, String caption);
    void setLogo(String groupName);
    void setLogo(Image image);
    void setContextTitle(String title);
    void setContent(View contentView);
    void setContext(View contextView);
    void setTool(String toolName);
}
