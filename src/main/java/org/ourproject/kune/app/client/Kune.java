package org.ourproject.kune.app.client;

import java.util.List;

import org.ourproject.kune.platf.client.KuneModule;
import org.ourproject.kune.platf.client.workspace.WorkspacePanel;

public class Kune {

    private static Kune instance;
    private WorkspacePanel workspace;

    private Kune() {
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

    public List getInstalledModules() {
	return null;
    }

    public KuneModule getDefaultModule() {
	return null;
    }
}
