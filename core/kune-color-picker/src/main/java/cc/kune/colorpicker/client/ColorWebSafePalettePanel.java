/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.colorpicker.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

// TODO: Auto-generated Javadoc
/**
 * A Web safe colors palette. See:
 * http://en.wikipedia.org/wiki/Web_colors#Web-safe_colors
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ColorWebSafePalettePanel implements ColorWebSafePaletteView {

  /** The palette grid. */
  Grid paletteGrid;
  
  /** The popup palette. */
  private PopupPanel popupPalette;
  
  /** The presenter. */
  private final ColorWebSafePalettePresenter presenter;

  /**
   * Instantiates a new color web safe palette panel.
   *
   * @param initPresenter the init presenter
   */
  public ColorWebSafePalettePanel(final ColorWebSafePalettePresenter initPresenter) {
    this.presenter = initPresenter;
  }

  /**
   * Creates the palette.
   */
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

  /* (non-Javadoc)
   * @see cc.kune.colorpicker.client.ColorWebSafePaletteView#hide()
   */
  @Override
  public void hide() {
    if (popupPalette != null) {
      popupPalette.hide();
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.colorpicker.client.ColorWebSafePaletteView#show(int, int)
   */
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
