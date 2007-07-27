package org.ourproject.kune.platf.client.workspace;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.KuneTool;

public class Workspace {
    private KuneTool[] tools;
    private String groupName;

    public Workspace(KunePlatform platform) {
    }

    public void setTools(KuneTool[] tools) {
        this.tools = tools;
    }

    public KuneTool[] getTools() {
        return tools;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    
    
}
