package org.ourproject.kune.platf.client.ui.gridmenu;

import com.calclab.suco.client.signal.Slot;

public class GridButton {

    private final String title;
    private final String icon;
    private final String tooltip;
    private final Slot<String> slot;

    public GridButton(final String icon, final String title, final String tooltip, final Slot<String> slot) {
	this.icon = icon;
	this.title = title;
	this.tooltip = tooltip;
	this.slot = slot;
    }

    public String getIcon() {
	return icon;
    }

    public Slot<String> getSlot() {
	return slot;
    }

    public String getTitle() {
	return title;
    }

    public String getTooltip() {
	return tooltip;
    }

}
