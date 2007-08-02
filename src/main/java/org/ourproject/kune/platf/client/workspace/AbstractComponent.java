package org.ourproject.kune.platf.client.workspace;

public abstract class AbstractComponent implements WorkspaceComponent {
    protected String encodedState;

    public AbstractComponent() {
	encodedState = "";
    }

    public void setEncodedState(String encodedState) {
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
