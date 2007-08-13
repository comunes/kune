package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.state.State;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public abstract class AbstractTool implements Tool {
    public final String name;
    private WorkspaceComponent content;
    private WorkspaceComponent context;
    protected State state;
    protected Dispatcher dispatcher;

    public AbstractTool(final String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setEnvironment(final Dispatcher dispatcher, final State state) {
	this.dispatcher = dispatcher;
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
