package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;

public class MenuItemDescriptor extends AbstractGuiActionDescrip {

    public MenuItemDescriptor(final MenuDescriptor parent, final AbstractAction action) {
        super(action);
        setParent(parent);
    }

    @Override
    public Class<?> getType() {
        return MenuItemDescriptor.class;
    }
}
