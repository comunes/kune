package org.ourproject.kune.platf.client.ui;

import com.calclab.suco.client.listener.Event;
import com.calclab.suco.client.listener.Listener;

public class MenuItem<T> {

    final String icon;
    final String title;
    private final Event<T> onClick;

    public MenuItem(final String icon, final String title, final Listener<T> listener) {
	this.icon = icon;
	this.title = title;
	this.onClick = new Event<T>("onClick");
	onClick(listener);
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

    public void onClick(final Listener<T> listener) {
	onClick.add(listener);
    }
}
