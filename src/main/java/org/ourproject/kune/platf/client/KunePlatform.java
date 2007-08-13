package org.ourproject.kune.platf.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.tool.Tool;

public class KunePlatform implements Register {
    private final List tools;
    private final HashMap actions;

    public KunePlatform() {
	this.tools = new ArrayList();
	this.actions = new HashMap();
    }

    public void addTool(final Tool tool) {
	tools.add(tool);
    }

    public void addAction(final String eventName, final Action action) {
	actions.put(eventName, action);
    }

    public void install(final ClientModule module) {
	module.configure(this);
    }

    public List getTools() {
	return tools;
    }

    public Map getActions() {
	return actions;
    }

}
