package cc.kune.common.client.actions;


public abstract class AbstractExtendedAction extends AbstractAction {
    public static final String NO_TEXT = null;
    public static final String NO_ICON = null;

    public AbstractExtendedAction() {
        super();
    }

    public AbstractExtendedAction(final String text) {
        this(text, null, null);
    }

    public AbstractExtendedAction(final String text, final String iconCls) {
        this(text, null, iconCls);
    }

    public AbstractExtendedAction(final String text, final String tooltip, final String iconCls) {
        super();
        super.putValue(Action.NAME, text);
        super.putValue(Action.SHORT_DESCRIPTION, tooltip);
        super.putValue(Action.SMALL_ICON, iconCls);
    }
}