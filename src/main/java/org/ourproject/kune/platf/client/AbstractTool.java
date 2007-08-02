package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;


public abstract class AbstractTool implements Tool {
    public final String name;
    protected String userHash;
    private WorkspaceComponent content;
    private WorkspaceComponent context;

    public AbstractTool(String name) {
        this.name = name;
    }

    public String getName() {
	return name;
    }

    public void useAsUser(String userHash) {
	this.userHash = userHash;
    }

    protected abstract WorkspaceComponent createContent();
    protected abstract WorkspaceComponent createContext();

    public WorkspaceComponent getContent() {
	if (content == null) {
	    content = createContent();
	}
	return content;
    }

    public WorkspaceComponent getContext() {
	if (context == null) {
	    context = createContext();
	}
	return context;
    }

}
