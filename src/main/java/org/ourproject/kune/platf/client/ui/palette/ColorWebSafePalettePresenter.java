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
 */
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
 */

package org.ourproject.kune.platf.client.ui.palette;

import com.calclab.suco.client.listener.Listener;

public class ColorWebSafePalettePresenter implements ColorWebSafePalette {

    private Listener<String> onColorSelected;
    private WebSafePaletteView view;

    public ColorWebSafePalettePresenter() {
    }

    public void hide() {
        this.view.hide();
    }

    public void init(final WebSafePaletteView view) {
        this.view = view;
    }

    public void show(final int left, final int top, final Listener<String> onColorSelected) {
        view.show(left, top);
        this.onColorSelected = onColorSelected;
    }

    protected void onColorSelected(final int row, final int col) {
        final String color = getColor(row, col);
        onColorSelected.onEvent(color);
    }

    private String getColor(final int row, final int col) {
        String color = null;
        final int pd = row * WebSafePaletteView.COLS + col;
        final int da = pd / 6;
        final int ra = pd % 6;
        final int aa = da - ra / 6;
        final int db = aa / 6;
        final int rb = aa % 6;
        final int rc = (db - rb / 6) % 6;
        color = "rgb(" + ra * 51 + ", " + rc * 51 + ", " + rb * 51 + ")";
        return color;
    }

}
