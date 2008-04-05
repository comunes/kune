package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class ChangeGroupWsThemeAction implements Action<String> {

    private final Workspace workspace;
    private final Session session;

    public ChangeGroupWsThemeAction(final Session session, final Workspace workspace) {
        this.session = session;
        this.workspace = workspace;
    }

    public void execute(final String theme) {
        onChangeGroupWsTheme(theme);
    }

    private void onChangeGroupWsTheme(final String theme) {
        Site.showProgressProcessing();
        final GroupServiceAsync server = GroupService.App.getInstance();
        server.changeGroupWsTheme(session.getUserHash(), session.getCurrentState().getGroup().getShortName(), theme,
                new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        workspace.setTheme(theme);
                        Site.hideProgress();
                    }
                });

    }

}
