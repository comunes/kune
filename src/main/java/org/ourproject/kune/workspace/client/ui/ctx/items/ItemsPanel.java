package org.ourproject.kune.workspace.client.ui.ctx.items;

import java.util.HashMap;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

class ItemsPanel extends VerticalPanel {
    private final HashMap fileIcons;

    public ItemsPanel() {
	addStyleName("kune-NavigationBar");
	addStyleName("Items");
	fileIcons = new HashMap();
	initFileIcons();
    }

    private void initFileIcons() {
	FolderContextImages Img = FolderContextImages.App.getInstance();
	// FIXME
	fileIcons.put("folder", Img.folder());
	fileIcons.put("file", Img.pageWhite());
    }

    public void add(final String name, final String type, final String event) {
	HorizontalPanel itemHP = new HorizontalPanel();
	add(itemHP);
	itemHP.add(((AbstractImagePrototype) fileIcons.get(type)).createImage());
	itemHP.add(new Hyperlink(name, event));
    }
}
