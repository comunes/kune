package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchListener;
import org.ourproject.kune.workspace.client.workspace.GroupLiveSearchComponent;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class AddGroupLiveSearchAction implements Action<EntityLiveSearchListener> {

    private final Workspace workspace;

    public AddGroupLiveSearchAction(final Workspace workspace) {
        this.workspace = workspace;
    }

    public void execute(final EntityLiveSearchListener listener) {
        onAddMemberGroupLiveSearchAction(listener);
    }

    private void onAddMemberGroupLiveSearchAction(final EntityLiveSearchListener listener) {
        GroupLiveSearchComponent groupLiveSearchComponent = workspace.getGroupLiveSearchComponent();
        groupLiveSearchComponent.addListener(listener);
        groupLiveSearchComponent.show();
    }
}
