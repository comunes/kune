package org.ourproject.kune.platf.client.extend;

import org.ourproject.kune.platf.client.Tool;
import org.ourproject.kune.platf.client.dispatch.Action;

public interface Register {
    public void addTool(Tool tool);
    public void addAction(String eventName, Action action);
}
