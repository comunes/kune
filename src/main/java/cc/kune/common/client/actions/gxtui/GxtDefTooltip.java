package cc.kune.common.client.actions.gxtui;

import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;

public class GxtDefTooltip extends ToolTipConfig {
    public enum Position {
        top, bottom, left, right
    }

    public GxtDefTooltip(final String text) {
        this(null, text, Position.left);
    }

    public GxtDefTooltip(final String title, final String text) {
        this(title, text, Position.left);
    }

    public GxtDefTooltip(final String title, final String text, final Position pos) {
        setText(text);
        setTitle(title);
        setMouseOffset(new int[] { 0, 0 });
        setAnchor(pos.name());
        setCloseable(false);
        setTrackMouse(true);
    }
}
