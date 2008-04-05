
package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class AddFolderAction implements Action<String> {
    private final Session session;
    private final StateManager stateManager;

    public AddFolderAction(final StateManager stateManager, final Session session) {
        this.stateManager = stateManager;
        this.session = session;
    }

    public void execute(final String name) {
        GroupDTO group = session.getCurrentState().getGroup();
        ContainerDTO container = session.getCurrentState().getFolder();
        addFolder(name, group, container);
    }

    private void addFolder(final String name, final GroupDTO group, final ContainerDTO container) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        server.addFolder(session.getUserHash(), group.getShortName(), container.getId(), name,
                new AsyncCallbackSimple<StateDTO>() {
                    public void onSuccess(final StateDTO state) {
                        Site.info(Kune.I18N.t("Folder created"));
                        stateManager.setRetrievedState(state);
                        // FIXME: Isn't using cache
                        stateManager.reloadContextAndTitles();
                        Site.hideProgress();
                    }
                });
    }
}
