package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.Action;

public class AttachToExtensionPointAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
        onAttachAction(services, (String) value, (View) extra);
    }

    private void onAttachAction(final Services services, final String id, final View view) {
        services.app.getWorkspace().attachToExtensionPoint(id, view);
    }
}
