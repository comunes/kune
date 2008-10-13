package org.ourproject.kune.workspace.client.skel;

import com.gwtext.client.widgets.Panel;

public class SummaryPanel extends Panel {

    private final WorkspaceSkeleton ws;

    public SummaryPanel(String title, String titleTooltip, WorkspaceSkeleton ws) {
        this.ws = ws;
        super.setBorder(false);
        super.setTitle("<span ext:qtip=\"" + titleTooltip + "\">" + title + "</span>");
        super.setAutoScroll(true);
    }

    public void doLayoutIfNeeded() {
        if (super.isRendered()) {
            super.doLayout(false);
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        doLayoutIfNeeded();
        ws.refreshSummary();
    }
}
