package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;

public class ButtonDescriptor extends GuiActionDescrip {

    public ButtonDescriptor(final AbstractAction action) {
        super(action);
    }

    @Override
    public Class<?> getType() {
        return ButtonDescriptor.class;
    }
}
