package org.ourproject.kune.workspace.client.ui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContextBottomBar extends HorizontalPanel {

    public ContextBottomBar() {
        addStyleName("kune-ContextBottomBar");
    }

    public void addWidget(Widget widget) {
        insert(widget, getWidgetCount()-1);
    }
}
