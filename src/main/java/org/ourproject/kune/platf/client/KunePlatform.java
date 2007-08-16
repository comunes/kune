package org.ourproject.kune.platf.client;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.tool.ClientTool;

public class KunePlatform implements Register {
    private final ArrayList tools;
    private final ArrayList actions;

    public KunePlatform() {
	this.tools = new ArrayList();
	this.actions = new ArrayList();
    }

    public void addTool(final ClientTool clientTool) {
	tools.add(clientTool);
    }

    public void addAction(final Action action) {
	actions.add(action);
    }

    public void install(final ClientModule module) {
	module.configure(this);
    }

    public List getTools() {
	return tools;
    }

    public ArrayList getActions() {
	return actions;
    }

}
