package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.extend.UIExtensionElement;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class AttachToExtensionPointAction implements Action<UIExtensionElement> {

    private final Workspace workspace;

    public AttachToExtensionPointAction(final Workspace workspace) {
        this.workspace = workspace;
    }

    public void execute(final UIExtensionElement element) {
        workspace.attachToExtensionPoint(element);
    }
}
