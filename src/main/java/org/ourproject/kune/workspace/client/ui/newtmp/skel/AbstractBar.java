package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AbstractBar extends Composite {
    private final HorizontalPanel horPanel;

    public AbstractBar() {
	horPanel = new HorizontalPanel();
	horPanel.setWidth("100%");
	initWidget(horPanel);
    }

    public void add(final Widget widget) {
	horPanel.add(widget);
    }

    public void addFill() {
	final Label emptyLabel = new Label("");
	horPanel.add(emptyLabel);
	horPanel.setCellWidth(emptyLabel, "100%");
    }

    public void addSeparator() {
	final Label emptyLabel = new Label("");
	emptyLabel.setStyleName("ytb-sep");
	emptyLabel.addStyleName("k-toolbar-sep");
	horPanel.add(emptyLabel);
    }

    public void addSpacer() {
	final Label emptyLabel = new Label("");
	emptyLabel.setStyleName("ytb-spacer");
	horPanel.add(emptyLabel);
    }

}
