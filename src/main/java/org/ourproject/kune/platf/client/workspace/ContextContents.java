package org.ourproject.kune.platf.client.workspace;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class ContextContents extends VerticalPanel {

    public ContextContents() {
        super();
        addStyleName("kune-ContextContents");
    }

    public void clear() {
        clear();
    }

    public void add(Widget widget) {
        insert(widget, getWidgetCount()-1);
    }
}