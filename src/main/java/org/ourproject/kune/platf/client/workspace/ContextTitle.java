package org.ourproject.kune.platf.client.workspace;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

class ContextTitle extends HorizontalPanel {
    private Label titleLabel = null;

    public ContextTitle() {
        // Initialize
        super();
        titleLabel = new Label();

        // Layout

        // Set properties
        this.addStyleName("kune-ContextTitle");
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }
}
