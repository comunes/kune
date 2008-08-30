package org.ourproject.kune.workspace.client.ui.newtmp.skel;

import org.ourproject.kune.platf.client.ui.DefaultBorderLayout;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.event.ContainerListener;
import com.gwtext.client.widgets.layout.FitLayout;

public class EntitySummary {
    private final Panel entityTools;
    private final Panel entitySummary;
    private final DefaultBorderLayout mainPanel;
    private final Toolbar trayBar;
    private final VerticalPanel vpTools;

    public EntitySummary() {
	mainPanel = new DefaultBorderLayout();
	entityTools = new Panel();
	entityTools.setLayout(new FitLayout());
	entityTools.setAutoHeight(true);
	vpTools = new VerticalPanel();
	entityTools.add(vpTools);
	entitySummary = new Panel();
	entityTools.setBorder(false);
	entitySummary.setBorder(false);
	entityTools.setAutoScroll(false);
	entitySummary.setAutoScroll(true);
	entitySummary.setCls("k-entity-summary");
	trayBar = mainPanel.createBottomBar(entitySummary, "k-site-traybar");
	mainPanel.add(entityTools, DefaultBorderLayout.Position.NORTH);
	mainPanel.add(entitySummary, DefaultBorderLayout.Position.CENTER);
	trayBar.addFill();
    }

    public void addInSummary(final Widget widget) {
	entitySummary.add(widget);
	entitySummary.syncSize();
	mainPanel.doLayoutIfNeeded();
    }

    public void addInTools(final Widget widget) {
	vpTools.add(widget);
	// entityTools.render(widget.getElement());
	if (entityTools.isRendered()) {
	    entityTools.syncSize();
	    entityTools.doLayout(false);
	    mainPanel.doLayoutIfNeeded();
	}
    }

    public void addListener(final ContainerListener listener) {
	entitySummary.addListener(listener);
    }

    public Panel getPanel() {
	return mainPanel.getPanel();
    }

    public Toolbar getSiteTraybar() {
	return trayBar;
    }

}