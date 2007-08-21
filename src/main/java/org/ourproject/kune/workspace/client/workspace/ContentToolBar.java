package org.ourproject.kune.workspace.client.workspace;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

class ContentToolBar extends HorizontalPanel {

    public ContentToolBar() {
        addStyleName("kune-ContextToolBar");
        add(new Label("A fuego en ContextToolBar"));
    }

}

