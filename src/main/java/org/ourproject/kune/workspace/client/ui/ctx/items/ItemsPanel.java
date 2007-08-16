package org.ourproject.kune.workspace.client.ui.ctx.items;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
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
    }

    public void add(final String name, final String type, final String event) {
	GWT.log("Item: " + name + " type: " + type, null);
	HorizontalPanel itemHP = new HorizontalPanel();
	add(itemHP);
	itemHP.add(((AbstractImagePrototype) fileIcons.get(type)).createImage());
	itemHP.add(new Hyperlink(name, event));
    }

    public void registerType(final String typeName, final AbstractImagePrototype image) {
	fileIcons.put(typeName, image);
    }
}
