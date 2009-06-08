package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;

public class MenuRadioItemDescriptor extends MenuCheckItemDescriptor {

    private final String group;

    public MenuRadioItemDescriptor(final MenuDescriptor parent, final AbstractAction action, final String group) {
        super(parent, action);
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public Class<?> getType() {
        return MenuRadioItemDescriptor.class;
    }
}
