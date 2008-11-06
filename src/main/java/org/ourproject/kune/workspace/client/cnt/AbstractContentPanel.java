package org.ourproject.kune.workspace.client.cnt;

import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Widget;

public class AbstractContentPanel {
    private final WorkspaceSkeleton ws;
    private Widget widget;

    public AbstractContentPanel(final WorkspaceSkeleton ws) {
        this.ws = ws;
    }

    public void attach() {
        if (!widget.isAttached()) {
            ws.getEntityWorkspace().setContent(widget);
        }
    }

    public void detach() {
        if (widget.isAttached()) {
            widget.removeFromParent();
        }
    }

    public void initWidget(Widget widget) {
        this.widget = widget;
    }
}
