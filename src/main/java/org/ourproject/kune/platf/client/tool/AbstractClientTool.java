
package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.dto.StateToken;

public abstract class AbstractClientTool implements ClientTool {
    protected final ToolTriggerDefault trigger;

    public AbstractClientTool(final String label) {
	trigger = new ToolTriggerDefault(getName(), label);
    }

    public ToolTrigger getTrigger() {
	return trigger;
    }

    public void setGroupState(final String groupShortName) {
	trigger.setState(new StateToken(groupShortName, getName(), null, null));
    }

}
