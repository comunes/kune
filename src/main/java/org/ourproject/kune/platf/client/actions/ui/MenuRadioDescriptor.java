package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;

public class MenuRadioDescriptor extends MenuCheckItemDescriptor {

    private transient final String group;

    public MenuRadioDescriptor(final MenuDescriptor parent, final AbstractAction action, final String group) {
        super(parent, action);
        this.group = group;
    }

    public String getGroup() {
        return group;
    }
}
