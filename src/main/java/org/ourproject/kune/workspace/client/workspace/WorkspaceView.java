package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.tool.ToolTrigger;

import com.google.gwt.user.client.ui.Image;

public interface WorkspaceView extends View {

    void setGroupLogo(String groupName);

    void setGroupLogo(Image image);

    void setContent(View contentView);

    void setContext(View contextView);

    void setTool(String toolName);

    void adjustSize(int windowWidth, int clientHeight);

    void addTab(ToolTrigger trigger);

    void setContentTitle(View view);

    void setContentSubTitle(View view);

    void setBottom(View view);

    void setGroupMembers(View view);

    void setParticipation(View view);

    void setSummary(View view);

    void addBottomIconComponent(View view);

    void setTheme(String theme);

    void setVisible(boolean visible);

    void setTags(View view);

    void setContentToolBar(View view);

    void setContentBottomToolBar(View view);

    void setPutYourLogoVisible(boolean visible);

    void registerUIExtensionPoints();

    void setComponents(WorkspaceUIComponents components);

}
