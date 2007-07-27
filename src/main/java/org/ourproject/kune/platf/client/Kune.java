package org.ourproject.kune.platf.client;

import org.ourproject.kune.chat.client.ChatTool;
import org.ourproject.kune.docs.client.DocumentTool;
import org.ourproject.kune.platf.client.workspace.ui.WorkspacePanel;

import com.google.gwt.core.client.GWT;

public class Kune {

    private static Kune instance;
    private KuneTool[] tools;
    public Translate t;

    private Kune() {
        t = (Translate) GWT.create(Translate.class);
    }

    public static Kune getInstance() {
        if (instance == null) {
            instance = new Kune();
        }
        return instance;
    }


    public KuneTool getDefaultTool() {
        return tools[0];
    }
}
