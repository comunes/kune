package org.ourproject.kune.platf.client.ui.gridmenu;

import com.calclab.suco.client.listener.Listener;

public class GridButton {

    private final String title;
    private final String icon;
    private final String tooltip;
    private final Listener<String> listener;

    public GridButton(final String icon, final String title, final String tooltip, final Listener<String> listener) {
	this.icon = icon;
	this.title = title;
	this.tooltip = tooltip;
	this.listener = listener;
    }

    public String getIcon() {
	return icon;
    }

    public Listener<String> getListener() {
	return listener;
    }

    public String getTitle() {
	return title;
    }

    public String getTooltip() {
	return tooltip;
    }

}
