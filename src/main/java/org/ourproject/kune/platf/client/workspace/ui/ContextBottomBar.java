package org.ourproject.kune.platf.client.workspace.ui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContextBottomBar extends HorizontalPanel {

    public ContextBottomBar() {
        super();
        addStyleName("kune-ContextBottomBar");
    }

    public void addWidget(Widget widget) {
        insert(widget, getWidgetCount()-1);
    }
}
