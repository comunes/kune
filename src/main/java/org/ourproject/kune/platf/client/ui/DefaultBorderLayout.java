package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;

public class DefaultBorderLayout {

    public enum Position {
	NORTH, CENTER, SOUTH, EAST, WEST
    }

    public static final int DEF_TOOLBAR_HEIGHT = 26;

    private static final int NO_SIZE = -666;

    private static RegionPosition[] regionPositions = new RegionPosition[] { RegionPosition.NORTH,
	    RegionPosition.CENTER, RegionPosition.SOUTH, RegionPosition.EAST, RegionPosition.WEST };

    private final Panel mainPanel;

    public DefaultBorderLayout() {
	mainPanel = new Panel();
	mainPanel.setLayout(new BorderLayout());
	mainPanel.setBorder(false);
    }

    public void add(final Panel panel, final Position position) {
	add(panel, position, false);
    }

    public void add(final Panel panel, final Position position, final boolean split) {
	add(panel, position, split, NO_SIZE);
    }

    public void add(final Panel panel, final Position position, final boolean split, final int size) {
	final RegionPosition regionPosition = regionPositions[position.ordinal()];
	final BorderLayoutData borderLayoutData = new BorderLayoutData(regionPosition);
	borderLayoutData.setSplit(split);
	if (split) {
	    borderLayoutData.setUseSplitTips(true);
	    borderLayoutData.setCollapseModeMini(true);
	}
	if (size != NO_SIZE) {
	    switch (position) {
	    case NORTH:
	    case SOUTH:
		panel.setHeight(size);
		break;
	    case EAST:
	    case WEST:
		panel.setWidth(size);
	    }
	}
	mainPanel.add(panel, borderLayoutData);
	doLayoutIfNeeded();
    }

    public void add(final Panel panel, final Position position, final int size) {
	add(panel, position, false, size);
    }

    public void add(final Panel panel, final Widget widget) {
	panel.add(widget);
	if (panel.isRendered()) {
	    panel.syncSize();
	    panel.doLayout();
	}
	doLayoutIfNeeded();
    }

    public void addStyle(final String style) {
	mainPanel.addClass(style);
    }

    public Toolbar createBottomBar(final Panel panel) {
	return createBottomBar(panel, null);
    }

    public Toolbar createBottomBar(final Panel panel, final String cssStyle) {
	final Toolbar bottomToolbar = new Toolbar();
	bottomToolbar.setHeight(DEF_TOOLBAR_HEIGHT);
	if (cssStyle != null) {
	    bottomToolbar.setCls(cssStyle);
	}
	panel.setBottomToolbar(bottomToolbar);
	return bottomToolbar;
    }

    public Toolbar createTopBar(final Panel panel) {
	return createTopBar(panel, null);
    }

    public Toolbar createTopBar(final Panel panel, final String cssStyle) {
	final Toolbar topToolbar = new Toolbar();
	topToolbar.setHeight(DEF_TOOLBAR_HEIGHT);
	if (cssStyle != null) {
	    topToolbar.setCls(cssStyle);
	}
	panel.setTopToolbar(topToolbar);
	return topToolbar;
    }

    public void doLayoutIfNeeded() {
	if (mainPanel.isRendered()) {
	    mainPanel.doLayout();
	}
    }

    public Panel getPanel() {
	return mainPanel;
    }

    public void removeStyle(final String style) {
	mainPanel.removeClass(style);
    }

    public void setBorder(final boolean border) {
	mainPanel.setBorder(border);
    }

    public void setPanel(final Panel panel, final Widget widget) {
	panel.clear();
	add(panel, widget);
	doLayoutIfNeeded();
    }
}
