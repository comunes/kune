package cc.kune.common.client.tooltip;

import cc.kune.common.client.tooltip.TooltipPosition.ArrowPosition;

public class TooltipPositionCalculator {

    public static TooltipPosition calculate(final int windowWitdh, final int windowHeight, final int widgetLeft,
            final int widgetTop, final int widgetWidth, final int widgetHeight, final int tooltipWidth,
            final int tooltipHeight) {
        // GWT.log("ww: " + windowWitdh + " wh: " + windowHeight + " widL: " +
        // widgetLeft + " widT: " + widgetTop
        // + " widW: " + widgetWidth + " widH: " + widgetHeight + " tW: " +
        // tooltipWidth + " tH: " + tooltipHeight);
        final boolean overflowsWidth = overflowsWidth(windowWitdh, widgetLeft, tooltipWidth);
        final boolean overflowsHeight = overflowsHeight(windowHeight, widgetTop, tooltipHeight);
        // GWT.log("ow: " + overflowsWidth + " oH: " + overflowsHeight);
        if (overflowsWidth && overflowsHeight) {
            // esta es mezcla de las anteriores (y falta)
            return new TooltipPosition(leftOverflow(widgetLeft, widgetWidth, tooltipWidth), widgetTop - tooltipHeight
                    - TooltipPosition.TOOLTIP_DISTANCE, ArrowPosition.SE, leftArrowOverflow(tooltipWidth), -2
                    * TooltipPosition.ARROW_SIZE);
        } else if (overflowsHeight) {
            return new TooltipPosition(leftNoOverflow(widgetLeft), widgetTop - tooltipHeight
                    - TooltipPosition.TOOLTIP_DISTANCE, ArrowPosition.SW, 0, -2 * TooltipPosition.ARROW_SIZE);
        } else if (overflowsWidth) {
            return new TooltipPosition(leftOverflow(widgetLeft, widgetWidth, tooltipWidth), topNoOverflow(widgetTop,
                    widgetHeight), ArrowPosition.NE, leftArrowOverflow(tooltipWidth), -2 * TooltipPosition.ARROW_SIZE
                    - 1);
            // 10 in the border width
        } else if (overflowsHeight) {
            return new TooltipPosition(onTopPositionTop(widgetLeft, widgetWidth), widgetTop - tooltipHeight
                    - TooltipPosition.TOOLTIP_DISTANCE, ArrowPosition.SW, TooltipPosition.ARROW_DEF_MARGIN, 0);
        } else {
            // Don't overflow
            return new TooltipPosition(leftNoOverflow(widgetLeft), topNoOverflow(widgetTop, widgetHeight),
                    ArrowPosition.NW, TooltipPosition.ARROW_DEF_MARGIN, -2 * TooltipPosition.ARROW_SIZE - 1);
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

    private static int onTopPositionTop(final int widgetTop, final int widgetHeight) {
        return topNoOverflow(widgetTop, widgetHeight);
    }

    private static boolean overflowsHeight(final int windowHeight, final int widgetTop, final int tooltipHeight) {
        return widgetTop + TooltipPosition.TOOLTIP_DISTANCE + tooltipHeight > windowHeight;
    }

    private static boolean overflowsWidth(final int windowWitdh, final int widgetLeft, final int tooltipWidth) {
        return widgetLeft + TooltipPosition.TOOLTIP_DISTANCE + tooltipWidth > windowWitdh;
    }

    private static int topNoOverflow(final int widgetTop, final int widgetHeight) {
        return widgetTop + widgetHeight + TooltipPosition.TOOLTIP_DISTANCE;
    }
}
