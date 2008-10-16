package org.ourproject.kune.platf.client.ui;

import com.calclab.suco.client.listener.Listener;

public class MenuItem<T> {

    final String icon;
    final String title;
    private final Listener<T> listener;

    public MenuItem(final String icon, final String title, final Listener<T> listener) {
        this.icon = icon;
        this.title = title;
        this.listener = listener;

    }

    public void fire(final T id) {
        listener.onEvent(id);
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }
}
