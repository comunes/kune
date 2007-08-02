package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.workspace.actions.ToolStateAction;


public abstract class AbstractTool implements Tool {
    public final String name;

    public AbstractTool(String name) {
        this.name = name;
    }

    public String getName() {
	return name;
    }

    public Action getStateAction() {
	return new ToolStateAction(this);
    }

}
