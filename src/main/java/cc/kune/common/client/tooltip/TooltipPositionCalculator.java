/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.common.client.tooltip;

import cc.kune.common.client.tooltip.TooltipPosition.ArrowPosition;

// TODO: Auto-generated Javadoc
/**
 * The Class TooltipPositionCalculator.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TooltipPositionCalculator {

  /**
   * Calculate.
   *
   * @param windowWitdh the window witdh
   * @param windowHeight the window height
   * @param widgetLeft the widget left
   * @param widgetTop the widget top
   * @param widgetWidth the widget width
   * @param widgetHeight the widget height
   * @param tooltipWidth the tooltip width
   * @param tooltipHeight the tooltip height
   * @return the tooltip position
   */
  public static TooltipPosition calculate(final int windowWitdh, final int windowHeight,
      final int widgetLeft, final int widgetTop, final int widgetWidth, final int widgetHeight,
      final int tooltipWidth, final int tooltipHeight) {
    final boolean overflowsWidth = overflowsWidth(windowWitdh, widgetLeft, tooltipWidth);
    final boolean overflowsHeight = overflowsHeight(windowHeight, widgetTop, tooltipHeight);
    if (overflowsWidth && overflowsHeight) {
      return new TooltipPosition(leftOverflow(widgetLeft, widgetWidth, tooltipWidth), widgetTop
          - tooltipHeight - TooltipPosition.TOOLTIP_DISTANCE, ArrowPosition.SE,
          leftArrowOverflow(tooltipWidth), -2 * TooltipPosition.ARROW_SIZE);
    } else if (overflowsHeight) {
      return new TooltipPosition(leftNoOverflow(widgetLeft), widgetTop - tooltipHeight
          - TooltipPosition.TOOLTIP_DISTANCE, ArrowPosition.SW, TooltipPosition.ARROW_DEF_MARGIN + 5, -2
          * TooltipPosition.ARROW_SIZE);
    } else if (overflowsWidth) {
      return new TooltipPosition(leftOverflow(widgetLeft, widgetWidth, tooltipWidth), topNoOverflow(
          widgetTop, widgetHeight), ArrowPosition.NE, leftArrowOverflow(tooltipWidth), -2
          * TooltipPosition.ARROW_SIZE - 1);
    } else {
      // Don't overflow
      return new TooltipPosition(leftNoOverflow(widgetLeft), topNoOverflow(widgetTop, widgetHeight),
          ArrowPosition.NW, TooltipPosition.ARROW_DEF_MARGIN + 5, -2 * TooltipPosition.ARROW_SIZE - 1);
    }
  }

  /**
   * Left arrow overflow.
   *
   * @param tooltipWidth the tooltip width
   * @return the int
   */
  private static int leftArrowOverflow(final int tooltipWidth) {
    return tooltipWidth - TooltipPosition.ARROW_DEF_MARGIN - 2 * 10;
  }

  /**
   * Left no overflow.
   *
   * @param widgetLeft the widget left
   * @return the int
   */
  private static int leftNoOverflow(final int widgetLeft) {
    return widgetLeft;
  }

  /**
   * Left overflow.
   *
   * @param widgetLeft the widget left
   * @param widgetWidth the widget width
   * @param tooltipWidth the tooltip width
   * @return the int
   */
  private static int leftOverflow(final int widgetLeft, final int widgetWidth, final int tooltipWidth) {
    return widgetLeft + widgetWidth - tooltipWidth - TooltipPosition.TOOLTIP_DISTANCE;
  }

  /**
   * Overflows height.
   *
   * @param windowHeight the window height
   * @param widgetTop the widget top
   * @param tooltipHeight the tooltip height
   * @return true, if successful
   */
  private static boolean overflowsHeight(final int windowHeight, final int widgetTop,
      final int tooltipHeight) {
    return widgetTop + TooltipPosition.TOOLTIP_DISTANCE + tooltipHeight > windowHeight;
  }

  /**
   * Overflows width.
   *
   * @param windowWitdh the window witdh
   * @param widgetLeft the widget left
   * @param tooltipWidth the tooltip width
   * @return true, if successful
   */
  private static boolean overflowsWidth(final int windowWitdh, final int widgetLeft,
      final int tooltipWidth) {
    return widgetLeft + TooltipPosition.TOOLTIP_DISTANCE + tooltipWidth > windowWitdh;
  }

  /**
   * Top no overflow.
   *
   * @param widgetTop the widget top
   * @param widgetHeight the widget height
   * @return the int
   */
  private static int topNoOverflow(final int widgetTop, final int widgetHeight) {
    return widgetTop + widgetHeight + TooltipPosition.TOOLTIP_DISTANCE;
  }
}
