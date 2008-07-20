package org.ourproject.kune.platf.client.ui.gridmenu;

import com.calclab.suco.client.signal.Signal;
import com.calclab.suco.client.signal.Slot;

public class GridMenuItem<T> {

    final String iconCls;
    final String title;
    private final Signal<T> onClick;

    public GridMenuItem(final String iconCls, final String title, final Slot<T> slot) {
	this.iconCls = iconCls;
	this.title = title;
	this.onClick = new Signal<T>("onClick");
	onClick(slot);
    }

    public void fire(final T id) {
	onClick.fire(id);
    }

    public String getIconCls() {
	return iconCls;
    }

    public String getTitle() {
	return title;
    }

    public void onClick(final Slot<T> slot) {
	onClick.add(slot);
    }
}
