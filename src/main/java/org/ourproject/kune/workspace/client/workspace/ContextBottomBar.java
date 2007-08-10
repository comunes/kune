package org.ourproject.kune.workspace.client.workspace;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

class ContextBottomBar extends HorizontalPanel {

    public ContextBottomBar() {
	addStyleName("kune-ContextBottomBar");
    }

    public void addWidget(final Widget widget) {
	insert(widget, getWidgetCount() - 1);
    }
}
