package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;

public class MenuDescriptor extends AbstractUIActionDescriptor {

    private static final MenuDescriptor NO_PARENT = null;
    private transient final MenuDescriptor parent;

    public MenuDescriptor(final AbstractAction action) {
        this(NO_PARENT, action);
    }

    public MenuDescriptor(final MenuDescriptor parent, final AbstractAction action) {
        super(action);
        this.parent = parent;
    }

    public MenuDescriptor getParent() {
        return parent;
    }

}
