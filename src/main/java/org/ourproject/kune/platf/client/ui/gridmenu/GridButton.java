/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */package org.ourproject.kune.platf.client.ui.gridmenu;

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
