/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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


// TODO: Auto-generated Javadoc
/**
 * The Class ColorWebSafePalettePresenter.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ColorWebSafePalettePresenter implements ColorWebSafePalette {

  /** The on color selected. */
  private OnColorSelectedListener onColorSelected;
  
  /** The view. */
  private ColorWebSafePaletteView view;

  /**
   * Instantiates a new color web safe palette presenter.
   */
  public ColorWebSafePalettePresenter() {
  }

  /**
   * Gets the color.
   *
   * @param row the row
   * @param col the col
   * @return the color
   */
  private String getColor(final int row, final int col) {
    String color = null;
    final int pd = row * ColorWebSafePaletteView.COLS + col;
    final int da = pd / 6;
    final int ra = pd % 6;
    final int aa = da - ra / 6;
    final int db = aa / 6;
    final int rb = aa % 6;
    final int rc = (db - rb / 6) % 6;
    color = "rgb(" + ra * 51 + ", " + rc * 51 + ", " + rb * 51 + ")";
    return color;
  }

  /* (non-Javadoc)
   * @see cc.kune.colorpicker.client.ColorWebSafePalette#hide()
   */
  @Override
  public void hide() {
    this.view.hide();
  }

  /**
   * Inits the.
   *
   * @param view the view
   */
  public void init(final ColorWebSafePaletteView view) {
    this.view = view;
  }

  /**
   * On color selected.
   *
   * @param row the row
   * @param col the col
   */
  protected void onColorSelected(final int row, final int col) {
    final String color = getColor(row, col);
   // FIXME onColorSelected.onColorChoose(color);
  }

  /* (non-Javadoc)
   * @see cc.kune.colorpicker.client.ColorWebSafePalette#show(int, int, cc.kune.colorpicker.client.OnColorSelectedListener)
   */
  @Override
  public void show(final int left, final int top, final OnColorSelectedListener onColorSelected) {
    view.show(left, top);
    this.onColorSelected = onColorSelected;
  }

}
