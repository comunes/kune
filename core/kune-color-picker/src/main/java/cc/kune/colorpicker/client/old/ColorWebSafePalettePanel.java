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
package cc.kune.colorpicker.client.old;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

/**
 *
 * A Web safe colors palette. See:
 * http://en.wikipedia.org/wiki/Web_colors#Web-safe_colors
 *
 */
public class ColorWebSafePalettePanel implements ColorWebSafePaletteView {

  Grid paletteGrid;
  private PopupPanel popupPalette;
  private final ColorWebSafePalettePresenter presenter;

  public ColorWebSafePalettePanel(final ColorWebSafePalettePresenter initPresenter) {
    this.presenter = initPresenter;
  }

  private void createPalette() {
    paletteGrid = new Grid(ROWS, COLS);

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
          final String currentColor = "#" + COLORS[c] + COLORS[a] + COLORS[b];
          paletteGrid.setText(row, col, " ");
          DOM.setStyleAttribute(paletteGrid.getCellFormatter().getElement(row, col), "backgroundColor",
              currentColor);
          n++;
        }
      }
    }
    paletteGrid.addStyleName("kune-WebSafePalette");
    paletteGrid.addTableListener(new TableListener() {
      @Override
      public void onCellClicked(final SourcesTableEvents sender, final int row, final int col) {
        presenter.onColorSelected(row, col);
      }
    });
  }

  @Override
  public void hide() {
    if (popupPalette != null) {
      popupPalette.hide();
    }
  }

  @Override
  public void show(final int left, final int top) {
    if (paletteGrid == null) {
      createPalette();
    }
    popupPalette = new PopupPanel(true, true);
    popupPalette.setVisible(false);
    popupPalette.show();
    popupPalette.setPopupPosition(left, top);
    popupPalette.setWidget(paletteGrid);
    popupPalette.setVisible(true);
  }
}
