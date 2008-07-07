package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class Toolbar {
    private final Panel container;
    private final AbstractBar horPanel;

    public Toolbar() {
	container = new Panel();
	container.setBorder(false);
	container.setBodyBorder(false);
	container.setHeight(DefaultBorderLayout.DEF_TOOLBAR_HEIGHT);
	container.setWidth("100%");
	container.setHeader(false);
	container.setBaseCls("x-toolbar");
	container.addClass("x-panel");
	horPanel = new AbstractBar();
	container.add(horPanel);
    }

    public void add(final Widget widget) {
	horPanel.add(widget);
	if (container.isRendered()) {
	    container.doLayout(false);
	}
    }

    public void addClass(final String cls) {
	container.addClass(cls);
    }

    public void addFill() {
	horPanel.addFill();
    }

    public void addSeparator() {
	horPanel.addSeparator();
    }

    public void addSpacer() {
	horPanel.addSpacer();
    }

    public Panel getPanel() {
	return container;
    }

}
