package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;

public class ButtonDescriptor extends AbstractGuiActionDescrip {

    public ButtonDescriptor(final AbstractAction action) {
        super(action);
    }

    @Override
    public Class<?> getType() {
        return ButtonDescriptor.class;
    }
}
