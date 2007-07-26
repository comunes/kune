package org.ourproject.kune.platf.client;

import org.ourproject.kune.chat.client.ChatTool;
import org.ourproject.kune.docs.client.DocumentTool;
import org.ourproject.kune.platf.client.workspace.WorkspacePanel;

import com.google.gwt.core.client.GWT;

public class Kune {

    private static Kune instance;
    private WorkspacePanel workspace;
    private KuneTool[] tools;
    public Translate t;

    private Kune() {
        t = (Translate) GWT.create(Translate.class);
        this.tools = new KuneTool[2];
        tools[0] = new DocumentTool();
        tools[1] = new ChatTool();
    }

    public String trans(String key) {
        return null;
    }

    public static Kune getInstance() {
        if (instance == null) {
            instance = new Kune();
        }
        return instance;
    }

    public WorkspacePanel getWorkspace() {
        if (workspace == null) {
            workspace = new WorkspacePanel();
        }
        return workspace;
    }

    public KuneTool[] getInstalledTools() {
        return tools;
    }

    public KuneTool getDefaultTool() {
        return tools[0];
    }
}
