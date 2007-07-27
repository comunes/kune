package org.ourproject.kune.platf.client.workspace.ui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

class ContextTitleBar extends HorizontalPanel {
    private Label titleLabel = null;

    public ContextTitleBar() {
        titleLabel = new Label();
        addStyleName("kune-ContextTitleBar");
        //FIXME: Borrar esto
        this.setTitle("Title");
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }
}
