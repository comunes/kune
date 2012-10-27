/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import cc.kune.common.client.tooltip.TooltipPosition.ArrowPosition;

public class TooltipPositionCalculator {

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

  private static int leftArrowOverflow(final int tooltipWidth) {
    return tooltipWidth - TooltipPosition.ARROW_DEF_MARGIN - 2 * 10;
  }

  private static int leftNoOverflow(final int widgetLeft) {
    return widgetLeft;
  }

  private static int leftOverflow(final int widgetLeft, final int widgetWidth, final int tooltipWidth) {
    return widgetLeft + widgetWidth - tooltipWidth - TooltipPosition.TOOLTIP_DISTANCE;
  }

  private static boolean overflowsHeight(final int windowHeight, final int widgetTop,
      final int tooltipHeight) {
    return widgetTop + TooltipPosition.TOOLTIP_DISTANCE + tooltipHeight > windowHeight;
  }

  private static boolean overflowsWidth(final int windowWitdh, final int widgetLeft,
      final int tooltipWidth) {
    return widgetLeft + TooltipPosition.TOOLTIP_DISTANCE + tooltipWidth > windowWitdh;
  }

  private static int topNoOverflow(final int widgetTop, final int widgetHeight) {
    return widgetTop + widgetHeight + TooltipPosition.TOOLTIP_DISTANCE;
  }
}
