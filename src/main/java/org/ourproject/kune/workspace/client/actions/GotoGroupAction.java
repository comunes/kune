package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.StateToken;

public class GotoGroupAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
	onGotoGroup(services, (String) value);
    }

    private void onGotoGroup(final Services services, final String groupShortName) {
	services.stateManager.setState(new StateToken(groupShortName));
    }
}
