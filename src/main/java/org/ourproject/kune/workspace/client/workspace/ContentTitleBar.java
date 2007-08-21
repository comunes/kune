package org.ourproject.kune.workspace.client.workspace;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

class ContentTitleBar extends HorizontalPanel {
    private Label titleLabel = null;

    public ContentTitleBar() {
        titleLabel = new Label();
        add(titleLabel);
        addStyleName("kune-ContextTitleBar");
        //FIXME: Borrar esto
        setTitleLabel("Title");
    }

    public void setTitleLabel(String title) {
        titleLabel.setText(title);
    }
}
