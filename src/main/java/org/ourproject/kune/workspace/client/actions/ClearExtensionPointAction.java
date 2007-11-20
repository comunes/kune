package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;

public class ClearExtensionPointAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
        onClearAction(services, (String) value);
    }

    private void onClearAction(final Services services, final String id) {
        services.app.getWorkspace().clearExtensionPoint(id);
    }
}
