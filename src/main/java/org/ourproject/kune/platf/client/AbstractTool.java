package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.workspace.WorkspaceComponent;


public abstract class AbstractTool implements Tool {
    public final String name;
    private WorkspaceComponent content;
    private WorkspaceComponent context;
    protected State state;

    public AbstractTool(String name) {
        this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setState(State state) {
	this.state = state;
    }

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

    protected abstract WorkspaceComponent createContent();
    protected abstract WorkspaceComponent createContext();
}
