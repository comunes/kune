package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.BaseAction;

public class ToolbarDescriptor extends AbstractGuiActionDescrip {

    public static final String TOOLBAR_CLEAR = "menuclear";

    public ToolbarDescriptor() {
        super(new BaseAction(null, null));
        super.getAction().putValue(TOOLBAR_CLEAR, false);
    }

    public void clear() {
        // Action detects changes in values, then we fire a change (whatever) to
        // fire this method in the UI
        putValue(TOOLBAR_CLEAR, !((Boolean) getValue(TOOLBAR_CLEAR)));
    }

    @Override
    public Class<?> getType() {
        return ToolbarDescriptor.class;
    }

    public void setText(final String text) {
        putValue(Action.NAME, text);
    }

}
