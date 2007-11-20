package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.Action;

public class DetachFromExtensionPointAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
        onDetachAction(services, (String) value, (View) extra);
    }

    private void onDetachAction(final Services services, final String id, final View view) {
        services.app.getWorkspace().detachFromExtensionPoint(id, view);
    }
}
