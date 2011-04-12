package cc.kune.common.client.tooltip;

public class TooltipPosition {

    public enum ArrowPosition {
        E, N, NE, NW, S, SE, SW, W
    }

    public static final int ARROW_DEF_MARGIN = 10;
    public static final int ARROW_SIZE = 7;
    public static final int TOOLTIP_DISTANCE = 7;

    private int arrowLeft;
    private ArrowPosition arrowPosition;
    private int arrowTop;
    private int left;
    private int top;

    public TooltipPosition(final int left, final int top, final ArrowPosition arrowPosition, final int arrowLeft,
            final int arrowTop) {
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
