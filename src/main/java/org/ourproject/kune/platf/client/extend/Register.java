package org.ourproject.kune.platf.client.extend;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.tool.ClientTool;

public interface Register {
    public void addTool(ClientTool clientTool);

    public void addAction(Action action);
}
