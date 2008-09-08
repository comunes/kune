package org.ourproject.kune.workspace.client.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class Toolbar {
    private final Panel container;
    private final HorizontalPanel childPanel;

    public Toolbar() {
	container = new Panel();
	container.setBorder(false);
	container.setBodyBorder(false);
	container.setHeight(DefaultBorderLayout.DEF_TOOLBAR_HEIGHT);
	container.setWidth("100%");
	container.setHeader(false);
	container.setBaseCls("x-toolbar");
	container.addClass("x-panel");
	childPanel = new HorizontalPanel();
	container.add(childPanel);
    }

    public void add(final Widget widget) {
	childPanel.add(widget);
	doLayoutIfNeeded();
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

    public void addStyleName(final String cls) {
	container.addClass(cls);
    }

    public void doLayoutIfNeeded() {
	if (container.isRendered()) {
	    container.doLayout(false);
	}
    }

    public Panel getPanel() {
	return container;
    }

    public void remove(final Widget widget) {
	childPanel.remove(widget);
	doLayoutIfNeeded();
    }

    public void removeAll() {
	childPanel.clear();
	doLayoutIfNeeded();
    }

}
