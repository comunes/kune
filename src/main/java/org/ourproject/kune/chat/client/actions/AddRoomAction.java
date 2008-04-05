
package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class AddRoomAction implements Action<String> {

    private final StateManager stateManager;
    private final Session session;

    public AddRoomAction(final Session session, final StateManager stateManager) {
        this.session = session;
        this.stateManager = stateManager;
    }

    public void execute(final String name) {
        GroupDTO group = session.getCurrentState().getGroup();
        ContainerDTO container = session.getCurrentState().getFolder();
        addRoom(name, group, container);
    }

    private void addRoom(final String name, final GroupDTO group, final ContainerDTO container) {
        Site.showProgressProcessing();
        ContentServiceAsync server = ContentService.App.getInstance();
        String groupShortName = group.getShortName();
        server.addRoom(session.getUserHash(), groupShortName, container.getId(), groupShortName + "-" + name,
                new AsyncCallbackSimple<StateDTO>() {
                    public void onSuccess(final StateDTO state) {
                        stateManager.setRetrievedState(state);
                        // FIXME: Isn't using cache (same in Add folder)
                        stateManager.reloadContextAndTitles();
                        Site.hideProgress();
                    }
                });
    }
}
