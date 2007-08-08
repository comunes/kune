package org.ourproject.kune.workspace.client;

public abstract class AbstractComponent implements WorkspaceComponent {
    protected String encodedState;

    public AbstractComponent() {
	encodedState = null;
    }

    public void setEncodedState(final String encodedState) {
	this.encodedState = encodedState;
    }

    public String getEncodedState() {
	return encodedState;
    }

    public void attach() {
    }

    public void detach() {
    }
}
