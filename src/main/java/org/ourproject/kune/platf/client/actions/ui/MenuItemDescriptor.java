package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;

public class MenuItemDescriptor extends AbstractUIActionDescriptor {

    private transient final MenuDescriptor parent;

    public MenuItemDescriptor(final MenuDescriptor parent, final AbstractAction action) {
        super(action);
        this.parent = parent;
    }

    public MenuDescriptor getParent() {
        return parent;
    }
}
