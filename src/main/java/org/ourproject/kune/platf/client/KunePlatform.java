
package org.ourproject.kune.platf.client;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dispatch.ActionEvent;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.tool.ClientTool;

public class KunePlatform implements Register {
    private final ArrayList<ClientTool> tools;
    private final ArrayList<ActionEvent<?>> actions;

    public KunePlatform() {
        this.tools = new ArrayList<ClientTool>();
        this.actions = new ArrayList<ActionEvent<?>>();
    }

    public void addTool(final ClientTool clientTool) {
        tools.add(clientTool);
    }

    @SuppressWarnings("unchecked")
    public void addAction(final String eventName, final Action<?> action) {
        actions.add(new ActionEvent(eventName, action));
    }

    public void install(final ClientModule module) {
        module.configure(this);
    }

    public List<ClientTool> getTools() {
        return tools;
    }

    public ArrayList<ActionEvent<?>> getActions() {
        return actions;
    }

}
