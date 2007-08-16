package org.ourproject.kune.workspace.client.ui.ctx.items;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ContextItemsPanel extends DockPanel implements ContextItemsView {
    private final ControlsPanel controls;
    private final ItemsPanel items;
    private final TopBar topBar;

    public ContextItemsPanel() {
	topBar = new TopBar();
	addTopBar(topBar);

	items = new ItemsPanel();
	add(items, DockPanel.NORTH);
	HTML expand = new HTML("<b></b>");
	add(expand, DockPanel.CENTER);
	controls = new ControlsPanel();
	add(controls, DockPanel.SOUTH);

	// FIXME: Test of width
	setWidth("100%");
	setHeight("100%");
	setCellWidth(topBar, "100%");
	expand.setWidth("100%");
	expand.setHeight("100%");
	setCellWidth(expand, "100%");
	setCellHeight(expand, "100%");
	addStyleName("kune-NavigationBar");
    }

    private void addTopBar(final Widget widget) {
	add(topBar, DockPanel.NORTH);
    }

    public void addItem(final String name, final String type, final String event) {
	items.add(name, type, event);
    }

    public void selectItem(final int index) {
    }

    public void clear() {
	items.clear();
    }

    public void setControlsVisible(final boolean isVisible) {
	controls.setVisible(isVisible);
    }

    public void setCurrentName(final String name) {
	topBar.setCurrentName(name);
    }

    public void setParentButtonEnabled(final boolean isVisible) {
	topBar.setParentButtonVisible(isVisible);
    }

    public void setParentTreeVisible(final boolean visible) {
	topBar.setParentTreeVisible(visible);
    }

    public void addGoParentListener(final ClickListener clickListener) {
	topBar.addGoParentListener(clickListener);
    }
}
