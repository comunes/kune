package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.workspace.Workspace;
import org.ourproject.kune.sitebar.client.SiteBarPresenter;
import org.ourproject.kune.sitebar.client.SiteBarTrans;

public interface Application {
    State getState();
    String getDefaultToolName();
    Workspace getWorkspace();
    SiteBarPresenter getSiteBar();
    Dispatcher getDispatcher();
    Tool getTool(String toolName);
}
