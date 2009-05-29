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
 */
package org.ourproject.kune.platf.client.ui.palette;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

/**
 * 
 * A Web safe colors palette. See:
 * http://en.wikipedia.org/wiki/Web_colors#Web-safe_colors
 * 
 */
public class ColorWebSafePalettePanel extends AbstractPalettePanel implements ColorWebSafePaletteView {

    private final transient ColorWebSafePalettePresenter presenter;

    public ColorWebSafePalettePanel(final ColorWebSafePalettePresenter initPresenter) {
        super();
        this.presenter = initPresenter;
    }

    @Override
    protected void createWidget() {
        final Grid paletteGrid = new Grid(ROWS, COLS);

        paletteGrid.setCellSpacing(1);
        // Put color values in the grid cells

        int row;
        int col;
        int num = 0;
        for (String element : COLORS) {
            for (String element2 : COLORS) {
                for (String element3 : COLORS) {
                    row = num / COLS;
                    col = num % COLS;
                    final String currentColor = "#" + element3 + element + element2;
                    paletteGrid.setText(row, col, " ");
                    DOM.setStyleAttribute(paletteGrid.getCellFormatter().getElement(row, col), "backgroundColor",
                            currentColor);
                    num++;
                }
            }
        }
        paletteGrid.addStyleName("kune-WebSafePalette");
        paletteGrid.addTableListener(new TableListener() {
            public void onCellClicked(final SourcesTableEvents sender, final int row, final int col) {
                presenter.onColorSelected(row, col);
            }
        });
        widget = paletteGrid;
    }
}
