package org.ourproject.kune.docs.client.ctx;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

public class DocumentContextPanel implements DocumentContextView {

    public DocumentContextPanel(final WorkspaceSkeleton ws) {
    }

    @Deprecated
    public void setContainer(final View view) {
	// ws.getEntityWorkspace().setContext((Widget) view);
    }

}
