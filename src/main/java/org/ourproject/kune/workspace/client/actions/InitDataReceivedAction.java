package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class InitDataReceivedAction implements Action<Object> {

    private final Workspace workspace;
    private final Session session;

    public InitDataReceivedAction(final Session session, final Workspace workspace) {
        this.session = session;
        this.workspace = workspace;
    }

    public void execute(final Object value) {
        onInitDataReceived();
    }

    private void onInitDataReceived() {
        workspace.setTheme(session.getDefaultWsTheme());
        workspace.getThemeMenuComponent().setThemes(session.getWsThemes());
    }

}
