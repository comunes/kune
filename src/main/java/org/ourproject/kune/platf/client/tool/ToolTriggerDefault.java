
package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.dto.StateToken;

public class ToolTriggerDefault implements ToolTrigger {
    private final String toolName;
    private final String label;
    private TriggerListener listener;

    public ToolTriggerDefault(final String toolName, final String caption) {
	this.toolName = toolName;
	this.label = caption;
    }

    public String getLabel() {
	return label;
    }

    public String getName() {
	return toolName;
    }

    public void setListener(final TriggerListener listener) {
	this.listener = listener;
    }

    public void setState(final String encoded) {
	listener.onStateChanged(encoded);
    }

    public void setState(final StateToken stateToken) {
	setState(stateToken.getEncoded());
    }

}
