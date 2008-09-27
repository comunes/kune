package org.ourproject.kune.workspace.client.skel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SimpleToolbar extends Composite {

    private final HorizontalPanel childPanel;
    private final HorizontalPanel mainPanel;

    public SimpleToolbar() {
	mainPanel = new HorizontalPanel();
	childPanel = new HorizontalPanel();
	mainPanel.add(childPanel);
	initWidget(mainPanel);
	mainPanel.setWidth("100%");
    }

    public void add(final Widget widget) {
	childPanel.add(widget);
	childPanel.setCellVerticalAlignment(widget, VerticalPanel.ALIGN_MIDDLE);
    }

    public Widget addFill() {
	final Label emptyLabel = new Label("");
	this.add(emptyLabel);
	childPanel.setCellWidth(emptyLabel, "100%");
	return emptyLabel;
    }

    public Widget addSeparator() {
	final Label emptyLabel = new Label("");
	emptyLabel.setStyleName("ytb-sep");
	emptyLabel.addStyleName("k-toolbar-sep");
	this.add(emptyLabel);
	return emptyLabel;
    }

    public Widget addSpacer() {
	final Label emptyLabel = new Label("");
	emptyLabel.setStyleName("ytb-spacer");
	this.add(emptyLabel);
	return emptyLabel;
    }

    public void removeAll() {
	childPanel.clear();
    }
}