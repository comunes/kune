package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchListener;
import org.ourproject.kune.workspace.client.workspace.UserLiveSearchComponent;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class AddUserLiveSearchAction implements Action<EntityLiveSearchListener> {

    private final Workspace workspace;

    public AddUserLiveSearchAction(final Workspace workspace) {
        this.workspace = workspace;
    }

    public void execute(final EntityLiveSearchListener listener) {
        onAddUserLiveSearchAction(listener);
    }

    private void onAddUserLiveSearchAction(final EntityLiveSearchListener listener) {
        UserLiveSearchComponent userLiveSearchComponent = workspace.getUserLiveSearchComponent();
        userLiveSearchComponent.addListener(listener);
        userLiveSearchComponent.show();
    }
}
