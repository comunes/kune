package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.workspace.client.Workspace;

public interface Application {
    State getState();
    String getDefaultToolName();
    Workspace getWorkspace();
    SiteBar getSiteBar();
    Dispatcher getDispatcher();
    Tool getTool(String toolName);
}
