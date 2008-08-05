package org.ourproject.kune.platf.client.ui;

import com.calclab.suco.client.signal.Signal;
import com.calclab.suco.client.signal.Slot;

public class MenuItem<T> {

    final String icon;
    final String title;
    private final Signal<T> onClick;

    public MenuItem(final String icon, final String title, final Slot<T> slot) {
	this.icon = icon;
	this.title = title;
	this.onClick = new Signal<T>("onClick");
	onClick(slot);
    }

    public void fire(final T id) {
	onClick.fire(id);
    }

    public String getIcon() {
	return icon;
    }

    public String getTitle() {
	return title;
    }

    public void onClick(final Slot<T> slot) {
	onClick.add(slot);
    }
}
