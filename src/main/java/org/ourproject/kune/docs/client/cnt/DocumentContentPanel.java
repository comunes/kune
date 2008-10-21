package org.ourproject.kune.docs.client.cnt;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Widget;

public class DocumentContentPanel implements DocumentContentView {

    private final WorkspaceSkeleton ws;

    public DocumentContentPanel(final WorkspaceSkeleton ws) {
        this.ws = ws;
    }

    public void setContent(final View view) {
        ws.getEntityWorkspace().setContent((Widget) view);
    }
}
