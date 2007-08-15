package org.ourproject.kune.docs.client.ui.ctx.folder;

import com.google.gwt.user.client.ui.DockPanel;

public class FolderContextPanel extends DockPanel implements FolderContentView {
    private final ControlsPanel controls;
    private final ItemsPanel items;

    public FolderContextPanel(final FolderContextListener listener) {
	add(new TopBar(), DockPanel.NORTH);
	items = new ItemsPanel();
	add(items, DockPanel.CENTER);

	controls = new ControlsPanel(listener);
	add(controls, DockPanel.SOUTH);
    }

    public void add(final String name, final String type, final String event) {
	items.add(name, type, event);
    }

    public void selectItem(final int index) {
    }

    public void clear() {
	items.clear();
    }

    public void setVisibleControls(final boolean isAddDocumentVisible, final boolean isAddFolderVisible) {
	controls.setVisibleControls(isAddDocumentVisible, isAddFolderVisible);
    }

}
