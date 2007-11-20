package org.ourproject.kune.workspace.client.workspace.ui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class DefaultContentPanel extends VerticalPanel {
    private final HTML content;

    public DefaultContentPanel() {
        content = new HTML("");
        add(content);
        this.setWidth("100%");
        this.setCellWidth(content, "100%");
        content.addStyleName("main-content");
    }

    public void setContent(final String text) {
        content.setHTML(text);
    }
}
