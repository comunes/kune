/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
 */
package org.ourproject.kune.platf.client.ui.palette;

import com.calclab.suco.client.events.Listener;

public abstract class AbstractPalettePresenter {

    protected Listener<String> onColorSelected;
    private AbstractPaletteView view;

    public void hide() {
        this.view.hide();
    }

    public void init(final AbstractPaletteView view) {
        this.view = view;
    }

    public void show(final int left, final int top, final Listener<String> onColorSelected) {
        view.show(left, top);
        this.onColorSelected = onColorSelected;
    }

    protected void onColorSelected(final String color) {
        onColorSelected.onEvent(color);
        view.hide();
    }

}
