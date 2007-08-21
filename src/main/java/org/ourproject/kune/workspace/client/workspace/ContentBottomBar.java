package org.ourproject.kune.workspace.client.workspace;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

class ContentBottomBar extends HorizontalPanel {

    public ContentBottomBar() {
	addStyleName("kune-ContextBottomBar");
    }

    public void addWidget(final Widget widget) {
	insert(widget, getWidgetCount() - 1);
    }
}
