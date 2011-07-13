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
package cc.kune.common.client.tooltip;

public class TooltipPosition {

  public enum ArrowPosition {
    E, N, NE, NW, S, SE, SW, W
  }

  public static final int ARROW_DEF_MARGIN = 6;
  public static final int ARROW_SIZE = 7;
  public static final int TOOLTIP_DISTANCE = 12;

  private int arrowLeft;
  private ArrowPosition arrowPosition;
  private int arrowTop;
  private int left;
  private int top;

  public TooltipPosition(final int left, final int top, final ArrowPosition arrowPosition,
      final int arrowLeft, final int arrowTop) {
    super();
    this.left = left;
    this.top = top;
    this.arrowPosition = arrowPosition;
    this.arrowLeft = arrowLeft;
    this.arrowTop = arrowTop;
  }

  public int getArrowLeft() {
    return arrowLeft;
  }

  public ArrowPosition getArrowPosition() {
    return arrowPosition;
  }

  public int getArrowTop() {
    return arrowTop;
  }

  public int getLeft() {
    return left;
  }

  public int getTop() {
    return top;
  }

  public void setArrowLeft(final int arrowLeft) {
    this.arrowLeft = arrowLeft;
  }

  public void setArrowPosition(final ArrowPosition arrowPosition) {
    this.arrowPosition = arrowPosition;
  }

  public void setArrowTop(final int arrowTop) {
    this.arrowTop = arrowTop;
  }

  public void setLeft(final int left) {
    this.left = left;
  }

  public void setTop(final int top) {
    this.top = top;
  }

}
