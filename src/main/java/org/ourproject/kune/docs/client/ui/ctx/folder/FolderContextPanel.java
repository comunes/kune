package org.ourproject.kune.docs.client.ui.ctx.folder;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;

public class FolderContextPanel extends DockPanel implements FolderContentView {
    private final ControlsPanel controls;
    private final ItemsPanel items;
    private final TopBar topBar;

    public FolderContextPanel(final FolderContextListener listener) {
	topBar = new TopBar(listener);
	add(topBar, DockPanel.NORTH);

	items = new ItemsPanel();
	add(items, DockPanel.NORTH);
	HTML expand = new HTML("<b></b>");
	add(expand, DockPanel.CENTER);
	controls = new ControlsPanel(listener);
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

    public void add(final String name, final String type, final String event) {
	items.add(name, type, event);
    }

    public void selectItem(final int index) {
    }

    public void clear() {
	items.clear();
    }

    public void setAddControlsVisibles(final boolean isAddDocumentVisible, final boolean isAddFolderVisible) {
	controls.setVisibleControls(isAddDocumentVisible, isAddFolderVisible);
    }

    public void setCurrentName(final String name) {
	topBar.setCurrentName(name);
    }

    public void setParentButtonVisible(final boolean isVisible) {
	topBar.setParentButtonVisible(isVisible);
    }
}
