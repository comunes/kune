/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 \*/
package org.ourproject.kune.platf.client.ui;

import com.calclab.suco.client.events.Listener;

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
