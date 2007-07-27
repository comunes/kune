package org.ourproject.kune.platf.client.workspace.ui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class ContextNavBar extends VerticalPanel {

    public ContextNavBar() {
        // Initialize
        super();

        // Layout

        // Set properties
        setStyleName("kune-ContextNavBar");
    }

    public void clear() {
        clear();
    }

    public void add(Widget widget) {
        insert(widget, getWidgetCount() - 1);
    }
}