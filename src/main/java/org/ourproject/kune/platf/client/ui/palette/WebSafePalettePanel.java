/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package org.ourproject.kune.platf.client.ui.palette;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

/**
 * 
 * A Web safe colors palette. See:
 * http://en.wikipedia.org/wiki/Web_colors#Web-safe_colors
 * 
 */
public class WebSafePalettePanel extends Composite implements WebSafePaletteView {

    Grid paletteGrid = null;

    private final WebSafePalettePresenter presenter;

    public WebSafePalettePanel(final WebSafePalettePresenter initPresenter) {
        this.presenter = initPresenter;

        paletteGrid = new Grid(ROWS, COLS);

        initWidget(paletteGrid);

        paletteGrid.setCellSpacing(1);
        // Put color values in the grid cells

        int row;
        int col;
        int n = 0;
        for (int a = 0; a < COLORS.length; a++) {
            for (int b = 0; b < COLORS.length; b++) {
                for (int c = 0; c < COLORS.length; c++) {
                    row = n / COLS;
                    col = n % COLS;
                    String currentColor = "#" + COLORS[c] + COLORS[a] + COLORS[b];
                    paletteGrid.getCellFormatter().setWidth(row, col, "12px");
                    paletteGrid.getCellFormatter().setHeight(row, col, "10px");
                    paletteGrid.setText(row, col, " ");
                    DOM.setStyleAttribute(paletteGrid.getCellFormatter().getElement(row, col), "backgroundColor",
                            currentColor);
                    n++;
                }
            }
        }
        paletteGrid.addStyleName("web-safe-palette");
        paletteGrid.addTableListener(new TableListener() {
            public void onCellClicked(final SourcesTableEvents sender, final int row, final int col) {
                presenter.onColorSelected(row, col);
            }
        });
    }

    public void hide() {
        // TODO Auto-generated method stub

    }

    public void show() {
        // TODO Auto-generated method stub

    }

    // public void show(final int left, final int top) {
    // paletteGrid.setVisible(true);
    // super.show();
    // super.setPopupPosition(left, top);
    // }

}
