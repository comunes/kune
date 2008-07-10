package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;

public class EntitySummary {
    private final Panel entityTools;
    private final Panel entitySummary;
    private final DefaultBorderLayout mainPanel;
    private final Toolbar trayBar;

    public EntitySummary() {
	mainPanel = new DefaultBorderLayout();
	entityTools = new Panel();
	entitySummary = new Panel();
	entitySummary.setCls("k-entity-summary");
	entityTools.setBorder(false);
	entitySummary.setBorder(false);
	entityTools.setAutoScroll(false);
	entitySummary.setAutoScroll(true);
	trayBar = mainPanel.createBottomBar(entitySummary, "k-site-traybar");
	mainPanel.add(entityTools, DefaultBorderLayout.Position.NORTH, 30);
	mainPanel.add(entitySummary, DefaultBorderLayout.Position.CENTER);
	trayBar.addFill();
    }

    public void addInSummary(final Widget widget) {
	entitySummary.add(widget);
	mainPanel.doLayoutIfNeeded();
    }

    public void addInTools(final Widget widget) {
	entityTools.add(widget);
	mainPanel.doLayoutIfNeeded();
    }

    public Panel getPanel() {
	return mainPanel.getPanel();
    }

    public Toolbar getSiteTraybar() {
	return trayBar;
    }

}
